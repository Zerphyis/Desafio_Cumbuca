package com.Zerphyis.strat.services;

import com.Zerphyis.strat.contasUsuarios.ContaUsuarios;
import com.Zerphyis.strat.contasUsuarios.DadosContasUsuario;
import com.Zerphyis.strat.contasUsuarios.RepositoryContaUsuarios;
import com.Zerphyis.strat.tratamentos.TratamentoException;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ServiceContasUsuarios {
    @Autowired
    RepositoryContaUsuarios repository;


    @Transactional
    public ResponseEntity cadastraConta( DadosContasUsuario dados){
        var conta= new ContaUsuarios(dados);
        repository.save(conta);
         return  ResponseEntity.ok(conta);
    }

    @Transactional
    public  ResponseEntity atualizarConta(Long id,DadosContasUsuario dados) {
        var verificarId=repository.findById(id);

        if (verificarId.isEmpty()) {
            throw new TratamentoException("Não foi possível encontrar o usuário");
        }

        ContaUsuarios contaAtualizada = verificarId.get();
        if (dados.nome() != null) {
            contaAtualizada.setNome(dados.nome());
        }
        if (dados.sobrenome() != null) {
            contaAtualizada.setSobrenome(dados.sobrenome());
        }
        if (dados.saldoInicial() != null) {
            contaAtualizada.setSaldoInicial(dados.saldoInicial());
        }
        repository.save(contaAtualizada);
        return ResponseEntity.ok(contaAtualizada);
    }

    @Transactional
    public ResponseEntity deletarConta(Long id){
        var verificar= repository.findById(id);
        if (verificar.isEmpty()) {
            throw new TratamentoException("Não foi possível encontrar o usuário pelo id");
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity listarcontas() {
        var contas = repository.findAll();
        return ResponseEntity.ok(contas);
        }
}


