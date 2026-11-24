package com.gestao.produtoslimpeza.repository;

import com.gestao.produtoslimpeza.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
