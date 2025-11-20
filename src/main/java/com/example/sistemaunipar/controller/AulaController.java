package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.dto.PresencaDTO;
import com.example.sistemaunipar.model.AulasDadas;
import com.example.sistemaunipar.model.AulasDadasPresencas;
import com.example.sistemaunipar.service.AulaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Aulas", description = "Endpoints para gerenciamento de aulas e presenças")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping("/api/aula/disciplina/{idDisciplina}")
    @Operation(summary = "Cadastrar uma nova aula para uma disciplina")
    public AulasDadas cadastrarAula(@PathVariable Long idDisciplina,
                                    @RequestBody AulasDadas dados) {
        return aulaService.criarAula(idDisciplina, dados);
    }

    @PostMapping("/api/auladada/{idAulaDada}")
    @Operation(summary = "Registrar presenças/faltas dos alunos em uma aula")
    public List<AulasDadasPresencas> registrarPresencas(@PathVariable Long idAulaDada,
                                                        @RequestBody List<PresencaDTO> presencas) {
        return aulaService.registrarPresencas(idAulaDada, presencas);
    }
}