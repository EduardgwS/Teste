package com.example.sistemaunipar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aulas_dadas_presencas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aula_dada_id", "aluno_id"})
})
public class AulasDadasPresencas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aula_dada_id")
    private AulasDadas aulaDada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    private Boolean falta;

    public AulasDadasPresencas() {}

    public Long getId() {
        return id;
    }

    public AulasDadas getAulaDada() {
        return aulaDada;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Boolean getFalta() {
        return falta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAulaDada(AulasDadas aulaDada) {
        this.aulaDada = aulaDada;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setFalta(Boolean falta) {
        this.falta = falta;
    }
}
