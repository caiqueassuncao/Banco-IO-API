package com.banco.repository;

import com.banco.model.Conta;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ContaRepository {

    private final Map<String, Conta> contas = new HashMap<>();

    public Conta salvar(Conta conta) {
        contas.put(conta.getId(), conta);
        return conta;
    }

    public Optional<Conta> buscarPorId(String id) {
        return Optional.ofNullable(contas.get(id));
    }

    public Optional<Conta> buscarPorCpf(String cpf) {
        return contas.values().stream()
            .filter(c -> c.getCpf().equals(cpf))
            .findFirst();
    }

    public List<Conta> listarTodas() {
        return new ArrayList<>(contas.values());
    }

    public boolean deletar(String id) {
        return contas.remove(id) != null;
    }

    public boolean cpfJaCadastrado(String cpf) {
        return contas.values().stream().anyMatch(c -> c.getCpf().equals(cpf));
    }
}