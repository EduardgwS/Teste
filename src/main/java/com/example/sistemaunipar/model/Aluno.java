package com.example.sistemaunipar.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "aluno", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf"),
        @UniqueConstraint(columnNames = "ra")
})
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID gerado automatics, não precisa colocar nada, idiota")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 14) //É bom pra n colocar CPF inválido igual IDIOTA
    private String cpf;

    @Column(nullable = false)
    private String ra;

    private Integer anoIngresso;
    private Integer periodoAtual;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRa() {
        return ra;
    }

    public Integer getAnoIngresso() {
        return anoIngresso;
    }

    public Integer getPeriodoAtual() {
        return periodoAtual;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void setAnoIngresso(Integer anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public void setPeriodoAtual(Integer periodoAtual) {
        this.periodoAtual = periodoAtual;
    }
}
