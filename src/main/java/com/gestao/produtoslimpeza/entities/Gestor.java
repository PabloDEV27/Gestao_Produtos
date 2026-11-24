package com.gestao.produtoslimpeza.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Gestor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String chaveAcesso;
    private String email;

    public Gestor(int id, String nome, String chaveAcesso, String email) {
        this.id = id;
        this.nome = nome;
        this.chaveAcesso = chaveAcesso;
        this.email = email;
    }

    public Gestor(String nome, String chaveAcesso, String email) {
        this.nome = nome;
        this.chaveAcesso = chaveAcesso;
        this.email = email;
    }

    public Gestor(){}

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

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
