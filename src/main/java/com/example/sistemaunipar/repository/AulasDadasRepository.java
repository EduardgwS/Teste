package com.example.sistemaunipar.repository;

import com.example.sistemaunipar.model.AulasDadas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulasDadasRepository extends JpaRepository<AulasDadas, Long> {
    Long countByDisciplinaId(Long disciplinaId);
}
