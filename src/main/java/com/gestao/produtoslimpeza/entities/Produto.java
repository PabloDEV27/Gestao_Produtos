package com.gestao.produtoslimpeza.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private int qtde;
    private int qtdeMinima;
    private int qtdeMaxima;
    @jakarta.persistence.Enumerated(EnumType.STRING)
    private TipoProduto tipo;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;


    public Produto(int id, String nome, int qtde) {
        this.id = id;
        this.nome = nome;
        this.qtde = qtde;
    }

    public Produto(String nome, int qtde) {
        this.nome = nome;
        this.qtde = qtde;
    }

    public Produto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    public int getQtdeMinima() {
        return qtdeMinima;
    }

    public void setQtdeMinima(int qtdeMinima) {
        this.qtdeMinima = qtdeMinima;
    }

    public int getQtdeMaxima() {
        return qtdeMaxima;
    }

    public void setQtdeMaxima(int qtdeMaxima) {
        this.qtdeMaxima = qtdeMaxima;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}
