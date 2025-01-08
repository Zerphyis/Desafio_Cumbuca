package com.Zerphyis.strat.contasUsuarios;

import java.time.LocalDate;

public record DadosContasUsuario(String nome, String sobrenome, String cpf, Long saldoInicial, TipoConta tipoConta) {

}
