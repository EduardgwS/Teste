package com.example.sistemaunipar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aluno_disciplina", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aluno_id", "disciplina_id"})
})
public class AlunoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Aluno aluno;

    @ManyToOne(optional = false)
    private Disciplina disciplina;

    private Double nota1Bim;
    private Double nota2Bim;

    private Integer faltas1Bim;
    private Integer faltas2Bim;

    private Boolean matriculado = true;

    @Enumerated(EnumType.STRING)
    private Situacao situacao = Situacao.EM_CURSO;

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Double getNota1Bim() {
        return nota1Bim;
    }

    public Double getNota2Bim() {
        return nota2Bim;
    }

    public Integer getFaltas1Bim() {
        return faltas1Bim;
    }

    public Integer getFaltas2Bim() {
        return faltas2Bim;
    }

    public Boolean getMatriculado() {
        return matriculado;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setNota1Bim(Double nota1Bim) {
        this.nota1Bim = nota1Bim;
    }

    public void setNota2Bim(Double nota2Bim) {
        this.nota2Bim = nota2Bim;
    }

    public void setFaltas1Bim(Integer faltas1Bim) {
        this.faltas1Bim = faltas1Bim;
    }

    public void setFaltas2Bim(Integer faltas2Bim) {
        this.faltas2Bim = faltas2Bim;
    }

    public void setMatriculado(Boolean matriculado) {
        this.matriculado = matriculado;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}
