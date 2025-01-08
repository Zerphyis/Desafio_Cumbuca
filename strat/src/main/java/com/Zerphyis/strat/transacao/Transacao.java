package com.Zerphyis.strat.transacao;

import com.Zerphyis.strat.contasUsuarios.ContaUsuarios;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_transacao")
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_recebedora")
    private ContaUsuarios IdRecebedora;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_enviadora")
    private  ContaUsuarios IdEnviadora;
    @NotNull
    private  Long valor;
    @FutureOrPresent
    private LocalDate horaDaTransacao;


    public Transacao(DadosTransacao dados){
        this.IdRecebedora=dados.IdRecebedora();
        this.IdEnviadora= dados.IdEnviadora();
        this.valor= dados.valor();
        this.horaDaTransacao= LocalDate.now();
    }

    public Long getIdRecebedora() {
        return IdRecebedora;
    }

    public void setIdRecebedora(Long idRecebedora) {
        IdRecebedora = idRecebedora;
    }

    public Long getIdEnviadora() {
        return IdEnviadora;
    }

    public void setIdEnviadora(Long idEnviadora) {
        IdEnviadora = idEnviadora;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public LocalDate getHoraDaTransacao() {
        return horaDaTransacao;
    }

    public void setHoraDaTransacao(LocalDate horaDaTransacao) {
        this.horaDaTransacao = horaDaTransacao;
    }
}
