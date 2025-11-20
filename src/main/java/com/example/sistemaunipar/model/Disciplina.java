package com.example.sistemaunipar.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "disciplina", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID gerado automatics, não precisa colocar nada, idiota")
    private Long id;

    @ManyToOne(optional = false)
    private Professor professor;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String descricao;

    @Column
    private String ementa;

    // getters
    public Long getId() {
        return id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEmenta() {
        return ementa;
    }

    // setters (IMPORTANTE — foram adicionados para resolver seu erro)
    public void setId(Long id) {
        this.id = id;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }
}
