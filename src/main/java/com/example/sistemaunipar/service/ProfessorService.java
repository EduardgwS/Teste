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

    // Método principal de Criação e Atualização (agora mais focado na validação)
    public Professor salvar(Professor p) {
        // Validações de campo obrigatório (devem ser feitas antes de salvar)
        validarCampos(p);

        // Validações de unicidade (funciona tanto para criar quanto para atualizar)
        validarUnicidade(p);

        return repo.save(p);
    }

    // Método para atualizar (Chama a busca e aplica as mudanças)
    public Professor atualizar(Long id, Professor p) {

        // 1. Busca o registro existente
        Professor professorExistente = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado para o ID: " + id));

        // 2. Configura o ID no objeto de entrada (para que a validação funcione corretamente)
        p.setId(id);

        // 3. Valida os campos do objeto de entrada (p)
        validarCampos(p);
        validarUnicidade(p);

        // 4. Mapeia os dados do objeto p (vindo do @RequestBody) para o objeto professorExistente.
        // Isso é crucial para evitar problemas de Attached/Detached entities (Optimistic Locking)
        professorExistente.setNome(p.getNome());
        professorExistente.setCpf(p.getCpf());
        professorExistente.setMatricula(p.getMatricula());
        // ... mapeie todos os outros campos aqui

        return repo.save(professorExistente);
    }

    // Método auxiliar para campos obrigatórios
    private void validarCampos(Professor p) {
        if (p.getMatricula() == null || p.getMatricula().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula é obrigatória");
        }
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
        }
        if (p.getCpf() == null || p.getCpf().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF é obrigatório");
        }
    }

    // Método auxiliar para unicidade
    private void validarUnicidade(Professor p) {
        // Validação de Matrícula
        repo.findByMatricula(p.getMatricula()).ifPresent(existing -> {
            // Se o ID for null (criação) OU o ID encontrado for diferente do ID atual (colisão)
            if (p.getId() == null || !existing.getId().equals(p.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Matrícula já cadastrada");
            }
        });

        // Validação de CPF
        repo.findByCpf(p.getCpf()).ifPresent(existing -> {
            // Se o ID for null (criação) OU o ID encontrado for diferente do ID atual (colisão)
            if (p.getId() == null || !existing.getId().equals(p.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
            }
        });
    }

    public Professor buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    public Professor buscarPorMatricula(String matricula) {
        return repo.findByMatricula(matricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com essa matrícula"));
    }

    public Professor buscarPorCpf(String cpf) {
        return repo.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado com esse CPF"));
    }

    public List<Professor> listarTodos() {
        return repo.findAll();
    }
}