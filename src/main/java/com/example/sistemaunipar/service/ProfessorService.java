package com.example.sistemaunipar.service;

import com.example.sistemaunipar.model.Professor;
import com.example.sistemaunipar.repository.ProfessorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repo;

    public ProfessorService(ProfessorRepository repo) {
        this.repo = repo;
    }

    public Professor salvar(Professor p) {
        // validações básicas de unicidade
        repo.findByMatricula(p.getMatricula()).ifPresent(existing -> {
            if (p.getId() == null || !existing.getId().equals(p.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Matrícula já cadastrada");
            }
        });
        repo.findByCpf(p.getCpf()).ifPresent(existing -> {
            if (p.getId() == null || !existing.getId().equals(p.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
            }
        });
        return repo.save(p);
    }

    public Professor buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    public List<Professor> listarTodos() {
        return repo.findAll();
    }
}
