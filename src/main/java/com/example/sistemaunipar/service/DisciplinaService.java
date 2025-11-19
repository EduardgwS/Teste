package com.example.sistemaunipar.service;

import com.example.sistemaunipar.dto.DisciplinaDTO;
import com.example.sistemaunipar.model.Disciplina;
import com.example.sistemaunipar.model.Professor;
import com.example.sistemaunipar.repository.DisciplinaRepository;
import com.example.sistemaunipar.repository.ProfessorRepository;
import com.example.sistemaunipar.repository.AlunoDisciplinaRepository;
import com.example.sistemaunipar.model.AlunoDisciplina;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository repo;
    private final ProfessorRepository professorRepo;
    private final AlunoDisciplinaRepository alunoDiscRepo;

    public DisciplinaService(DisciplinaRepository repo,
                             ProfessorRepository professorRepo,
                             AlunoDisciplinaRepository alunoDiscRepo) {
        this.repo = repo;
        this.professorRepo = professorRepo;
        this.alunoDiscRepo = alunoDiscRepo;
    }

    public Disciplina salvar(Disciplina d) {
        // caso você envie objeto completo com professor já setado
        return repo.save(d);
    }

    public Disciplina criarComDto(DisciplinaDTO dto) {
        if (dto.professorId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "professorId é obrigatório");
        }
        Professor p = professorRepo.findById(dto.professorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        // validação de código único
        repo.findByCodigo(dto.codigo).ifPresent(existing -> {
            if (dto.id == null || !existing.getId().equals(dto.id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Código de disciplina já existe");
            }
        });

        Disciplina d = new Disciplina();
        if (dto.id != null) d.setId(dto.id);
        d.setProfessor(p);
        d.setCodigo(dto.codigo);
        d.setDescricao(dto.descricao);
        d.setEmenta(dto.ementa);
        return repo.save(d);
    }

    public Disciplina buscarPorCodigo(String codigo) {
        return repo.findByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
    }

    public List<Disciplina> listarTodas() {
        return repo.findAll();
    }

    public List<Disciplina> listarPorProfessor(Long professorId) {
        // verifica existência do professor (opcional)
        professorRepo.findById(professorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        return repo.findByProfessorId(professorId);
    }

    public List<AlunoDisciplina> listarMatriculados(Long idDisciplina) {
        return alunoDiscRepo.findByDisciplinaIdAndMatriculadoTrue(idDisciplina);
    }
}