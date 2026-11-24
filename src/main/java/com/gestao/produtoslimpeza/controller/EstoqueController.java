package com.gestao.produtoslimpeza.controller;


import com.gestao.produtoslimpeza.entities.Estoque;
import com.gestao.produtoslimpeza.entities.Produto;
import com.gestao.produtoslimpeza.repository.EstoqueRepository;
import com.gestao.produtoslimpeza.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/estoques")
public class EstoqueController {

    private final EstoqueRepository repository;
    private final ProdutoRepository produtoRepository;

    public EstoqueController(EstoqueRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }
    @PostMapping
    public String criarEstoque(@RequestBody Estoque estoque, @RequestParam int novaQtde) {
        try {
            // Atualiza a quantidade do produto principal
            Produto p = produtoRepository.findById(estoque.getProduto().getId()).get();
            p.setQtde(novaQtde);
            produtoRepository.save(p);

            // Salva o histórico de movimentação
            repository.save(estoque);

            // REGRA: Checa limites para enviar "E-mail"
            if (p.getQtde() < p.getQtdeMinima()) {
                System.out.println("Enviando E-MAIL: Produto " + p.getNome() + " ABAIXO do estoque mínimo!");
                return "Salvo! Alerta e-mail enviado: Estoque Baixo.";
            } else if (p.getQtde() > p.getQtdeMaxima()) {
                System.out.println("Enviando E-MAIL: Produto " + p.getNome() + " ACIMA do estoque máximo!");
                return "Salvo! Alerta e-mail enviado: Estoque Alto.";
            }

            return "Estoque atualizado com sucesso!";
        } catch (Exception e) {
            return "Dados inválidos";
        }
    }

    @GetMapping
    public List<Estoque> mostrarEstoque(){
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletarEstoque(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public void buscarPorId(@PathVariable int id){
        repository.findById(id);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable int id, @RequestBody Estoque e){
        Estoque estoque = repository.getReferenceById(id);
        estoque.setGestor(e.getGestor());
        estoque.setProduto(e.getProduto());
    }
}
