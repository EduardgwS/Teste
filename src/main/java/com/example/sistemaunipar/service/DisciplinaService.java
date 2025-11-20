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

        // 1. Validação: professorId é obrigatório
        if (dto.professorId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "professorId é obrigatório");
        }

        // 2. Busca o professor. Se não encontrar, lança 404.
        Professor p = professorRepo.findById(dto.professorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        // 3. Validação de código único
        // Como é uma criação (POST), o código NÃO pode existir.
        repo.findByCodigo(dto.codigo).ifPresent(existing -> {
            // Se encontrar, lança CONFLICT (409)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Código de disciplina já existe");
        });

        // 4. Cria a nova entidade Disciplina
        Disciplina d = new Disciplina();
        // d.setId(dto.id);  <-- LINHA REMOVIDA

        // 5. Mapeia os dados do DTO para a Entity
        d.setProfessor(p);
        d.setCodigo(dto.codigo);
        d.setDescricao(dto.descricao);
        d.setEmenta(dto.ementa);

        // 6. Salva e retorna
        return repo.save(d);
    }

    public Disciplina atualizarComDto(Long id, DisciplinaDTO dto) {

        // 1. Busca a disciplina existente. Se não encontrar, lança 404.
        Disciplina disciplinaExistente = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Disciplina não encontrada para o ID: " + id));

        // 2. Valida se o professorId foi fornecido e existe. (Regra de Negócio)
        if (dto.professorId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "professorId é obrigatório");
        }
        Professor novoProfessor = professorRepo.findById(dto.professorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Professor não encontrado"));

        // 3. Validação de Código Único:
        // Garante que o novo código não pertence a outra disciplina (que não seja a disciplina atual).
        repo.findByCodigo(dto.codigo).ifPresent(existing -> {
            // Se o código for encontrado E o ID do código encontrado for diferente do ID que estamos atualizando
            if (!existing.getId().equals(disciplinaExistente.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Código de disciplina já existe");
            }
        });

        // 4. Aplica as alterações no objeto existente

        // O ID já está definido no objeto 'disciplinaExistente'.

        // O professor pode mudar
        disciplinaExistente.setProfessor(novoProfessor);

        // Demais campos
        disciplinaExistente.setCodigo(dto.codigo);
        disciplinaExistente.setDescricao(dto.descricao);
        disciplinaExistente.setEmenta(dto.ementa);

        // 5. Salva (o Hibernate entende que é uma atualização)
        return repo.save(disciplinaExistente);
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