package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.dto.DisciplinaDTO;
import com.example.sistemaunipar.model.AlunoDisciplina;
import com.example.sistemaunipar.model.Disciplina;
import com.example.sistemaunipar.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin(origins = "*")
@Tag(name = "Disciplina", description = "Gerencia as Disciplinas    ")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova Disciplina")
    public Disciplina criar(@RequestBody DisciplinaDTO dto) {
        return service.criarComDto(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Disciplina")
    public Disciplina atualizar(@PathVariable Long id, @RequestBody DisciplinaDTO dto) {
        // Você terá que ajustar o service.criarComDto(dto) para se tornar um
        // service.atualizarComDto(id, dto) que lida com a lógica de update e ID.
        return service.atualizarComDto(id, dto);
    }

    @GetMapping("/todas")
    @Operation(summary = "Lista todas as disciplinas")
    public List<Disciplina> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/professor/{idProfessor}")
    @Operation(summary = "Listar discipina por ID de professor")
    public List<Disciplina> listarPorProfessor(@PathVariable Long idProfessor) {
        return service.listarPorProfessor(idProfessor);
    }

    @GetMapping("/{idDisciplina}/matriculados")
    @Operation(summary = "Lista alunos matriculados em uma disciplina")
    public List<AlunoDisciplina> listarMatriculados(@PathVariable Long idDisciplina) {
        return service.listarMatriculados(idDisciplina);
    }

    // Movido para o final para evitar conflito de rotas
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Busca disciplina por ID   ")
    public Disciplina buscarPorCodigo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }
}