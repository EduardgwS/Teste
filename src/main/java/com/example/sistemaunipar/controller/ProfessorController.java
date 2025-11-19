package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.model.Professor;
import com.example.sistemaunipar.service.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping
    public Professor criar(@RequestBody Professor p) {
        return service.salvar(p);
    }

    @PutMapping
    public Professor atualizar(@RequestBody Professor p) {
        return service.salvar(p);
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/todos")
    public List<Professor> listarTodos() {
        return service.listarTodos();
    }
}
