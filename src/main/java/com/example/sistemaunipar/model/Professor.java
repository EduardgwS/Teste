package com.example.sistemaunipar.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "professor", uniqueConstraints = {
        @UniqueConstraint(columnNames = "matricula"),
        @UniqueConstraint(columnNames = "cpf")
})
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID gerado automatics, não precisa colocar nada, idiota")
    private Long id;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
