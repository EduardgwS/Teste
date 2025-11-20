package com.example.sistemaunipar.repository;

import com.example.sistemaunipar.model.AulasDadasPresencas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AulasDadasPresencasRepository extends JpaRepository<AulasDadasPresencas, Long> {

    /**
     * Busca um registro de presença específico para uma aula e aluno.
     * Útil para evitar duplicatas ao registrar presenças.
     */
    Optional<AulasDadasPresencas> findByAulaDadaIdAndAlunoId(Long aulaDadaId, Long alunoId);
}