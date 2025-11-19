package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.dto.NotaFaltaDTO;
import com.example.sistemaunipar.model.Aluno;
import com.example.sistemaunipar.model.AlunoDisciplina;
import com.example.sistemaunipar.service.AlunoService;
import com.example.sistemaunipar.service.MatriculaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aluno")
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService alunoService;
    private final MatriculaService matriculaService;

    public AlunoController(AlunoService alunoService, MatriculaService matriculaService) {
        this.alunoService = alunoService;
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public Aluno cadastrar(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @PutMapping
    public Aluno atualizar(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @GetMapping("/todos")
    public List<Aluno> listarTodos() {
        return alunoService.listarTodos();
    }

    // Novo endpoint de matricular
    @PostMapping("/{idAluno}/matricular/{idDisciplina}")
    public AlunoDisciplina matricular(@PathVariable Long idAluno, @PathVariable Long idDisciplina) {
        return matriculaService.matricularAluno(idAluno, idDisciplina);
    }

    @PutMapping("/{idAluno}/disciplina/{idDisciplina}/1bim")
    public AlunoDisciplina atualizar1Bim(@PathVariable Long idAluno,
                                         @PathVariable Long idDisciplina,
                                         @RequestBody NotaFaltaDTO dto) {
        return matriculaService.atualizar1Bim(idAluno, idDisciplina, dto.nota1Bim, dto.faltas1Bim);
    }

    @PutMapping("/{idAluno}/disciplina/{idDisciplina}/2bim")
    public AlunoDisciplina atualizar2Bim(@PathVariable Long idAluno,
                                         @PathVariable Long idDisciplina,
                                         @RequestBody NotaFaltaDTO dto) {
        return matriculaService.atualizar2Bim(idAluno, idDisciplina, dto.nota2Bim, dto.faltas2Bim);
    }

    @GetMapping("/{id}/boletim")
    public List<AlunoDisciplina> boletim(@PathVariable Long id) {
        return matriculaService.boletimDoAluno(id);
    }
}
