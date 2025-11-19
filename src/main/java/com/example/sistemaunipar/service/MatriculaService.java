package com.example.sistemaunipar.service;

import com.example.sistemaunipar.model.*;
import com.example.sistemaunipar.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatriculaService {

    private final AlunoDisciplinaRepository repo;
    private final AulasDadasRepository aulasRepo;
    private final AlunoRepository alunoRepo;
    private final DisciplinaRepository disciplinaRepo;

    public MatriculaService(AlunoDisciplinaRepository repo,
                            AulasDadasRepository aulasRepo,
                            AlunoRepository alunoRepo,
                            DisciplinaRepository disciplinaRepo) {
        this.repo = repo;
        this.aulasRepo = aulasRepo;
        this.alunoRepo = alunoRepo;
        this.disciplinaRepo = disciplinaRepo;
    }

    public List<AlunoDisciplina> boletimDoAluno(Long alunoId) {
        // verifica aluno existe
        alunoRepo.findById(alunoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        return repo.findByAlunoId(alunoId);
    }

    @Transactional
    public AlunoDisciplina matricularAluno(Long alunoId, Long disciplinaId) {
        var aluno = alunoRepo.findById(alunoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        var disciplina = disciplinaRepo.findById(disciplinaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));

        // checar se já matriculado
        var existente = repo.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId);
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aluno já matriculado nessa disciplina");
        }

        AlunoDisciplina ad = new AlunoDisciplina();
        ad.setAluno(aluno);
        ad.setDisciplina(disciplina);
        ad.setMatriculado(true);
        ad.setSituacao(Situacao.EM_CURSO);
        ad.setNota1Bim(null);
        ad.setNota2Bim(null);
        ad.setFaltas1Bim(0);
        ad.setFaltas2Bim(0);

        return repo.save(ad);
    }

    @Transactional
    public AlunoDisciplina atualizar1Bim(Long alunoId, Long disciplinaId, Double nota, Integer faltas) {
        AlunoDisciplina ad = repo.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        ad.setNota1Bim(nota);
        ad.setFaltas1Bim(faltas);
        ad.setSituacao(Situacao.EM_CURSO);

        return repo.save(ad);
    }

    @Transactional
    public AlunoDisciplina atualizar2Bim(Long alunoId, Long disciplinaId, Double nota, Integer faltas) {
        AlunoDisciplina ad = repo.findByAlunoIdAndDisciplinaId(alunoId, disciplinaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));

        ad.setNota2Bim(nota);
        ad.setFaltas2Bim(faltas);

        double n1 = ad.getNota1Bim() == null ? 0.0 : ad.getNota1Bim();
        double n2 = ad.getNota2Bim() == null ? 0.0 : ad.getNota2Bim();
        double media = (n1 + n2) / 2.0;

        int faltasTotal = (ad.getFaltas1Bim() == null ? 0 : ad.getFaltas1Bim())
                + (ad.getFaltas2Bim() == null ? 0 : ad.getFaltas2Bim());

        long totalAulas = aulasRepo.countByDisciplinaId(disciplinaId);

        double percentualPresenca;
        if (totalAulas > 0) {
            percentualPresenca = (double) (totalAulas - faltasTotal) / (double) totalAulas;
        } else {
            percentualPresenca = 1.0; // se não tem aula registrada, não reprova por falta
        }

        if (media >= 6.0 && percentualPresenca >= 0.25) {
            ad.setSituacao(Situacao.APROVADO);
            ad.setMatriculado(false);
        } else {
            ad.setSituacao(Situacao.REPROVADO);
            ad.setMatriculado(true);
        }

        return repo.save(ad);
    }
}
