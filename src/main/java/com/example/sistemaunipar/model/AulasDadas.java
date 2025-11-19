package com.example.sistemaunipar.model;

import jakarta.persistence.*;

@Entity
public class AulasDadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    public AulasDadas() {}

    public AulasDadas(int quantidade, Disciplina disciplina) {
        this.quantidade = quantidade;
        this.disciplina = disciplina;
    }

    public Long getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
