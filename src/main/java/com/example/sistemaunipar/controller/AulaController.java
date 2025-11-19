package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.dto.PresencaDTO;
import com.example.sistemaunipar.model.AulasDadas;
import com.example.sistemaunipar.model.AulasDadasPresencas;
import com.example.sistemaunipar.service.AulaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping("/api/aula/disciplina/{idDisciplina}")
    public AulasDadas cadastrarAula(@PathVariable Long idDisciplina,
                                    @RequestBody AulasDadas dados) {
        return aulaService.criarAula(idDisciplina, dados);
    }

    @PostMapping("/api/auladada/{idAulaDada}")
    public List<AulasDadasPresencas> registrarPresencas(@PathVariable Long idAulaDada,
                                                        @RequestBody List<PresencaDTO> presencas) {
        return aulaService.registrarPresencas(idAulaDada, presencas);
    }
}
