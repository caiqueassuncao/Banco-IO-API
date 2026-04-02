package com.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transacao {

    public enum Tipo {
        DEPOSITO,
        SAQUE,
        TRANSFERENCIA_SAIDA,
        TRANSFERENCIA_ENTRADA
    }

    private String id;
    private Tipo tipo;
    private BigDecimal valor;
    private String descricao;
    private BigDecimal saldoAposOperacao;
    private LocalDateTime realizadaEm;

    public Transacao(Tipo tipo, BigDecimal valor, String descricao, BigDecimal saldoAposOperacao) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.saldoAposOperacao = saldoAposOperacao;
        this.realizadaEm = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Tipo getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public BigDecimal getSaldoAposOperacao() { return saldoAposOperacao; }
    public LocalDateTime getRealizadaEm() { return realizadaEm; }
}