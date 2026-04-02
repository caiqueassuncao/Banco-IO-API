package com.banco.service;

import com.banco.model.Conta;
import com.banco.model.Transacao;
import com.banco.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContaService {

    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    public Conta criarConta(String titular, String cpf, BigDecimal saldoInicial) {
        if (repository.cpfJaCadastrado(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }
        if (saldoInicial == null) saldoInicial = BigDecimal.ZERO;
        if (saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
        Conta conta = new Conta(titular, cpf, saldoInicial);
        return repository.salvar(conta);
    }

    public List<Conta> listarContas() {
        return repository.listarTodas();
    }

    public Conta buscarConta(String id) {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new NoSuchElementException("Conta não encontrada: " + id));
    }

    public void deletarConta(String id) {
        if (!repository.deletar(id)) {
            throw new NoSuchElementException("Conta não encontrada: " + id);
        }
    }

    public Conta depositar(String id, BigDecimal valor) {
        validarValorPositivo(valor);
        Conta conta = buscarConta(id);
        conta.depositar(valor);
        return conta;
    }

    public Conta sacar(String id, BigDecimal valor) {
        validarValorPositivo(valor);
        Conta conta = buscarConta(id);
        conta.sacar(valor);
        return conta;
    }

    public synchronized void transferir(String origemId, String destinoId, BigDecimal valor) {
        validarValorPositivo(valor);
        if (origemId.equals(destinoId)) {
            throw new IllegalArgumentException("Origem e destino não podem ser a mesma conta.");
        }
        Conta origem = buscarConta(origemId);
        Conta destino = buscarConta(destinoId);

        origem.sacar(valor);
        destino.depositar(valor);

        origem.registrarTransferenciaSaida(valor, destino.getTitular());
        destino.registrarTransferenciaEntrada(valor, origem.getTitular());
    }

    public List<Transacao> historico(String id) {
        return buscarConta(id).getHistorico();
    }

    private void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser positivo.");
        }
    }
}
