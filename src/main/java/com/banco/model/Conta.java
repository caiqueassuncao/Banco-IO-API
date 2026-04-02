package com.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conta {

    private String id;
    private String titular;
    private String cpf;
    private BigDecimal saldo;
    private LocalDateTime criadaEm;
    private List<Transacao> historico;

    public Conta(String titular, String cpf, BigDecimal saldoInicial) {
        this.id = UUID.randomUUID().toString();
        this.titular = titular;
        this.cpf = cpf;
        this.saldo = saldoInicial;
        this.criadaEm = LocalDateTime.now();
        this.historico = new ArrayList<>();
    }

    public synchronized void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
        this.historico.add(new Transacao(
            Transacao.Tipo.DEPOSITO,
            valor,
            "Depósito de R$ " + valor,
            this.saldo
        ));
    }

    public synchronized void sacar(BigDecimal valor) {
        if (valor.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente. Saldo atual: R$ " + this.saldo);
        }
        this.saldo = this.saldo.subtract(valor);
        this.historico.add(new Transacao(
            Transacao.Tipo.SAQUE,
            valor,
            "Saque de R$ " + valor,
            this.saldo
        ));
    }

    public void registrarTransferenciaSaida(BigDecimal valor, String destinatario) {
        this.historico.add(new Transacao(
            Transacao.Tipo.TRANSFERENCIA_SAIDA,
            valor,
            "Transferência enviada para " + destinatario + " — R$ " + valor,
            this.saldo
        ));
    }

    public void registrarTransferenciaEntrada(BigDecimal valor, String remetente) {
        this.historico.add(new Transacao(
            Transacao.Tipo.TRANSFERENCIA_ENTRADA,
            valor,
            "Transferência recebida de " + remetente + " — R$ " + valor,
            this.saldo
        ));
    }

    public String getId() { return id; }
    public String getTitular() { return titular; }
    public String getCpf() { return cpf; }
    public BigDecimal getSaldo() { return saldo; }
    public LocalDateTime getCriadaEm() { return criadaEm; }
    public List<Transacao> getHistorico() { return historico; }
}