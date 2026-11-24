package com.gestao.produtoslimpeza.controller;


import com.gestao.produtoslimpeza.entities.Gestor;
import com.gestao.produtoslimpeza.repository.GestorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/gestores")
public class GestorController {

    private final GestorRepository repository;

    public GestorController(GestorRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public String criarGestor(@RequestBody Gestor gestores){
        try{
            return "Salvo com sucesso!" + repository.save(gestores);
        } catch (Exception e){
            return "Dados inválidos";
        }
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestBody Gestor tentativaLogin) {
        // Busca no banco se existe algum gestor com essa chave
        Gestor gestorEncontrado = repository.findByChaveAcesso(tentativaLogin.getChaveAcesso());

        if (gestorEncontrado != null) {
            return "Login aprovado! Bem-vindo, " + gestorEncontrado.getNome();
        } else {
            return "Acesso negado. Chave inválida.";
        }
    }

    @GetMapping
    public List<Gestor> mostrarGestor(){
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletarGestor(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public void buscarPorId(@PathVariable int id){
        repository.findById(id);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable int id, @RequestBody Gestor g){
        Gestor gestor = repository.getReferenceById(id);
        gestor.setNome(g.getNome());
    }
}
