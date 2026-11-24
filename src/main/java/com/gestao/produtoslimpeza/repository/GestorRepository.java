package com.gestao.produtoslimpeza.repository;

import com.gestao.produtoslimpeza.entities.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestorRepository extends JpaRepository<Gestor, Integer> {

    Gestor findByChaveAcesso(String chaveAcesso);

}
