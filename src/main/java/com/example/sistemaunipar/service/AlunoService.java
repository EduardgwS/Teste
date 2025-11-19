package com.example.sistemaunipar.service;

import com.example.sistemaunipar.model.Aluno;
import com.example.sistemaunipar.repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repo;

    public AlunoService(AlunoRepository repo) {
        this.repo = repo;
    }

    public Aluno salvar(Aluno aluno) {
        // valida CPF
        repo.findByCpf(aluno.getCpf()).ifPresent(existing -> {
            if (aluno.getId() == null || !existing.getId().equals(aluno.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
            }
        });
        // valida RA
        repo.findByRa(aluno.getRa()).ifPresent(existing -> {
            if (aluno.getId() == null || !existing.getId().equals(aluno.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "RA já cadastrado");
            }
        });
        return repo.save(aluno);
    }

    public Aluno buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
    }

    public List<Aluno> listarTodos() {
        return repo.findAll();
    }
}
