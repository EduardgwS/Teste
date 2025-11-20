package com.example.sistemaunipar.controller;

import com.example.sistemaunipar.dto.NotaFaltaDTO;
import com.example.sistemaunipar.model.Aluno;
import com.example.sistemaunipar.model.AlunoDisciplina;
import com.example.sistemaunipar.service.AlunoService;
import com.example.sistemaunipar.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aluno")
@CrossOrigin(origins = "*")
@Tag(name = "Aluno", description = "Gerencia os dados dos alunos")
public class AlunoController {

    private final AlunoService alunoService;
    private final MatriculaService matriculaService;

    public AlunoController(AlunoService alunoService, MatriculaService matriculaService) {
        this.alunoService = alunoService;
        this.matriculaService = matriculaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo aluno")
    public Aluno cadastrar(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    // 1. Altere o mapping para incluir /{id}
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um aluno")
    public Aluno atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        // 3. Atribua o ID recebido da URL ao objeto aluno
        aluno.setId(id);
        // 4. Salve
        return alunoService.salvar(aluno);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar todos os alunos")
    public List<Aluno> listarTodos() {
        return alunoService.listarTodos();
    }

    @PostMapping("/{idAluno}/matricular/{idDisciplina}")
    @Operation(summary = "Matricular aluno em uma disciplina")
    public AlunoDisciplina matricular(@PathVariable Long idAluno, @PathVariable Long idDisciplina) {
        return matriculaService.matricularAluno(idAluno, idDisciplina);
    }

    @PutMapping("/{idAluno}/disciplina/{idDisciplina}/1bim")
    @Operation(summary = "Atualizar nota e faltas do 1º bimestre")
    public AlunoDisciplina atualizar1Bim(@PathVariable Long idAluno,
                                         @PathVariable Long idDisciplina,
                                         @RequestBody NotaFaltaDTO dto) {
        return matriculaService.atualizar1Bim(idAluno, idDisciplina, dto.nota1Bim, dto.faltas1Bim);
    }

    @PutMapping("/{idAluno}/disciplina/{idDisciplina}/2bim")
    @Operation(summary = "Atualizar nota e faltas do 2º bimestre (calcula situação final)")
    public AlunoDisciplina atualizar2Bim(@PathVariable Long idAluno,
                                         @PathVariable Long idDisciplina,
                                         @RequestBody NotaFaltaDTO dto) {
        return matriculaService.atualizar2Bim(idAluno, idDisciplina, dto.nota2Bim, dto.faltas2Bim);
    }

    @GetMapping("/{id}/boletim")
    @Operation(summary = "Obter boletim completo do aluno")
    public List<AlunoDisciplina> boletim(@PathVariable Long id) {
        return matriculaService.boletimDoAluno(id);
    }
}