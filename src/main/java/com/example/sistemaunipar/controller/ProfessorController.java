package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.model.Professor;
import com.example.sistemaunipar.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
@CrossOrigin(origins = "*")
@Tag(name = "Professor", description = "Endpoints para gerenciamento de professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo professor")
    public Professor criar(@RequestBody Professor p) {
        return service.salvar(p);
    }

// ProfessorController.java (Apenas o método PUT)

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um professor")
    public Professor atualizar(@PathVariable Long id, @RequestBody Professor p) {
        // Agora chama o novo método de atualização do Service
        return service.atualizar(id, p);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar professor por ID")
    public Professor buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar todos os professores")
    public List<Professor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Buscar professor por matrícula")
    public Professor buscarPorMatricula(@PathVariable String matricula) {
        return service.buscarPorMatricula(matricula);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar professor por CPF")
    public Professor buscarPorCpf(@PathVariable String cpf) {
        return service.buscarPorCpf(cpf);
    }
}