package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.dto.DisciplinaDTO;
import com.example.sistemaunipar.model.AlunoDisciplina;
import com.example.sistemaunipar.model.Disciplina;
import com.example.sistemaunipar.service.DisciplinaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service) {
        this.service = service;
    }

    @PostMapping
    public Disciplina criar(@RequestBody DisciplinaDTO dto) {
        return service.criarComDto(dto);
    }

    @PutMapping
    public Disciplina atualizar(@RequestBody DisciplinaDTO dto) {
        return service.criarComDto(dto); // reusa para criar/atualizar
    }

    @GetMapping("/{codigo}")
    public Disciplina buscarPorCodigo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    @GetMapping("/todas")
    public List<Disciplina> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/professor/{idProfessor}")
    public List<Disciplina> listarPorProfessor(@PathVariable Long idProfessor) {
        return service.listarPorProfessor(idProfessor);
    }

    @GetMapping("/{idDisciplina}/matriculados")
    public List<AlunoDisciplina> listarMatriculados(@PathVariable Long idDisciplina) {
        return service.listarMatriculados(idDisciplina);
    }
}
