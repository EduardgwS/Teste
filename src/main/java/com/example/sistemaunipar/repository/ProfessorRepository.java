package com.example.sistemaunipar.repository;

import com.example.sistemaunipar.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByCpf(String cpf);
    Optional<Professor> findByMatricula(String matricula);
}
