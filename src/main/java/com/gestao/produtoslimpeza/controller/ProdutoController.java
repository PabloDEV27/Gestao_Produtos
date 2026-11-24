package com.gestao.produtoslimpeza.controller;

import com.gestao.produtoslimpeza.entities.Gestor;
import com.gestao.produtoslimpeza.entities.Produto;
import com.gestao.produtoslimpeza.entities.TipoProduto;
import com.gestao.produtoslimpeza.repository.GestorRepository;
import com.gestao.produtoslimpeza.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;
    private final GestorRepository gestorRepository;

    public ProdutoController(ProdutoRepository repository, GestorRepository gestorRepository) {
        this.repository = repository;
        this.gestorRepository = gestorRepository;
    }
    @PostMapping
    public String criarProduto(@RequestBody Map<String, Object> payload){
        try{
            String senhaGestor = (String) payload.get("senhaGestor");
            Gestor gestor = gestorRepository.findByChaveAcesso(senhaGestor);

            if (gestor == null){
                return "Erro: Não autorizado!";
            }

            Produto p = new Produto();
            p.setNome((String) payload.get("nome"));
            p.setTipo(TipoProduto.valueOf(payload.get("tipo").toString().toUpperCase()));
            p.setQtdeMinima(Integer.parseInt(payload.get("qtdeMinima").toString()));
            p.setQtdeMaxima(Integer.parseInt(payload.get("qtdeMaxima").toString()));
            p.setQtde(0); // Começa com 0, quem abastece é o estoque

            repository.save(p);
            return "Salvo com sucesso!";
        } catch (Exception e){
            return "Dados inválidos" + e.getMessage();
        }
    }

    @GetMapping
    public List<Produto> mostrarProduto(){
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public void buscarPorId(@PathVariable int id){
        repository.findById(id);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable int id, @RequestBody Produto p){
        Produto produto = repository.getReferenceById(id);
        produto.setNome(p.getNome());
        produto.setQtde(p.getQtde());
        repository.save(produto);
    }
}
