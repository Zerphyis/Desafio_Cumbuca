package com.Zerphyis.strat.contasUsuarios;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class ContaUsuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

   private Long id;
    @NotBlank

    private String nome;
    @NotBlank

    private String sobrenome;
    @NotBlank

    private String cpf;
    @NotNull

    private Long saldoInicial;
    @FutureOrPresent
    private LocalDate horaDeCriacao;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;


    public ContaUsuarios(DadosContasUsuario dados){
        this.nome= dados.nome();
        this.sobrenome= dados.sobrenome();
        this.cpf= dados.cpf();
        this.tipoConta= dados.tipoConta();
        this.saldoInicial=dados.saldoInicial();
        this.horaDeCriacao=LocalDate.now();

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Long saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public LocalDate getHoraDeCriacao() {
        return horaDeCriacao;
    }

    public void setHoraDeCriacao(LocalDate horaDeCriacao) {
        this.horaDeCriacao = horaDeCriacao;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}
