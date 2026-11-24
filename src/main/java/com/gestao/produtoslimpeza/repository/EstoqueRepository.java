package com.gestao.produtoslimpeza.repository;

import com.gestao.produtoslimpeza.entities.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
}
