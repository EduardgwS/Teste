package com.example.sistemaunipar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "aulas_dadas")
public class AulasDadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ADICIONE ESTA LINHA: O ID deve ser gerado pelo servidor, não enviado pelo cliente.
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID da aula dada, gerado automaticamente")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @Column(nullable = false)
    private LocalDate data;

    @Column(length = 500)
    private String observacoes;

    // Construtores
    public AulasDadas() {}

    public AulasDadas(Disciplina disciplina, LocalDate data, String observacoes) {
        this.disciplina = disciplina;
        this.data = data;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}