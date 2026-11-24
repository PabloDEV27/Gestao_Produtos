package com.gestao.produtoslimpeza.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Estoque {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "gestor_id", nullable = false)
    private Gestor gestor;
    @org.hibernate.annotations.CreationTimestamp
    private java.time.LocalDateTime dataMovimento;

    public Estoque(int id, Produto produto, Gestor gestor) {
        this.id = id;
        this.produto = produto;
        this.gestor = gestor;
    }

    public Estoque(Produto produto, Gestor gestor) {
        this.produto = produto;
        this.gestor = gestor;
    }

    public Estoque(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }
}
