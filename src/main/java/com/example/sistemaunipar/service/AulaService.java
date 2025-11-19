package com.example.sistemaunipar.service;

import com.example.sistemaunipar.dto.PresencaDTO;
import com.example.sistemaunipar.model.*;
import com.example.sistemaunipar.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class AulaService {

    private final DisciplinaRepository disciplinaRepo;
    private final AulasDadasRepository aulasRepo;
    private final AulasDadasPresencasRepository presencasRepo;
    private final AlunoRepository alunoRepo;

    public AulaService(DisciplinaRepository disciplinaRepo,
                       AulasDadasRepository aulasRepo,
                       AulasDadasPresencasRepository presencasRepo,
                       AlunoRepository alunoRepo) {
        this.disciplinaRepo = disciplinaRepo;
        this.aulasRepo = aulasRepo;
        this.presencasRepo = presencasRepo;
        this.alunoRepo = alunoRepo;
    }

    /**
     * Cria uma aula associada à disciplina informada.
     */
    public AulasDadas criarAula(Long idDisciplina, AulasDadas dados) {
        Disciplina d = disciplinaRepo.findById(idDisciplina)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        dados.setDisciplina(d);
        return aulasRepo.save(dados);
    }

    /**
     * Recebe uma lista de PresencaDTO e cria os registros de presença/falta para a aula informada.
     */
    @Transactional
    public List<AulasDadasPresencas> registrarPresencas(Long idAulaDada, List<PresencaDTO> lista) {
        AulasDadas aula = aulasRepo.findById(idAulaDada)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        List<AulasDadasPresencas> salvas = new ArrayList<>();
        for (PresencaDTO p : lista) {
            Aluno aluno = alunoRepo.findById(p.alunoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado: " + p.alunoId));

            // opcional: checar se já existe registro para essa aula+aluno e atualizar em vez de duplicar
            // aqui fazemos insert simples (poderia verificar e usar save/update)
            AulasDadasPresencas presenca = new AulasDadasPresencas();
            presenca.setAulaDada(aula);
            presenca.setAluno(aluno);
            presenca.setFalta(Boolean.TRUE.equals(p.falta)); // evita nulls
            salvas.add(presencasRepo.save(presenca));
        }
        return salvas;
    }
}
