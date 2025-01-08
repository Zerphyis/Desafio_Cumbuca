package com.Zerphyis.strat.services;

import com.Zerphyis.strat.contasUsuarios.ContaUsuarios;
import com.Zerphyis.strat.contasUsuarios.RepositoryContaUsuarios;
import com.Zerphyis.strat.transacao.DadosTransacao;
import com.Zerphyis.strat.transacao.RepositoryTransacao;
import com.Zerphyis.strat.transacao.Transacao;
import com.Zerphyis.strat.tratamentos.TratamentoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceTransacao {
    @Autowired
    RepositoryContaUsuarios repoContas;

    @Autowired
    RepositoryTransacao repository;

    @Transactional
    public ResponseEntity cadastrarTransacao(DadosTransacao dados){
        var IdEnviadora=repoContas.findById(dados.IdEnviadora());
        var IdRecebedora=repoContas.findById(dados.IdRecebedora());

        if(IdRecebedora.isEmpty()){
            throw  new TratamentoException("Não foi encontrado conta com esse Id");

        }
        if(IdEnviadora.isEmpty()){
            throw  new TratamentoException("Não foi encontrado conta com esse Id");
        }
        ContaUsuarios contaEnviadora = IdEnviadora.get();
        ContaUsuarios contaRecebedora = IdRecebedora.get();


        if (contaEnviadora.getSaldoInicial() < dados.valor()) {
            throw new TratamentoException("Saldo insuficiente na conta enviadora");
        }


        contaEnviadora.setSaldoInicial(contaEnviadora.getSaldoInicial() - dados.valor());
        contaRecebedora.setSaldoInicial(contaRecebedora.getSaldoInicial() + dados.valor());


        repoContas.save(contaEnviadora);
        repoContas.save(contaRecebedora);


        var novaTransacao = new Transacao(dados);
        repository.save(novaTransacao);

        return ResponseEntity.ok(novaTransacao);
    }
    @Transactional
    public ResponseEntity estornarTransacao(Long transacaoId, Long usuarioId) {
        var transacao = repository.findById(transacaoId);

        if (transacao.isEmpty()) {
            throw new TratamentoException("Transação não encontrada");
        }

        Transacao transacaoExistente = transacao.get();


        if (!transacaoExistente.getIdEnviadora().equals(usuarioId)) {
            throw new TratamentoException("Esta transação não foi iniciada por você");
        }


        var contaEnviadora = repoContas.findById(transacaoExistente.getIdEnviadora());
        var contaRecebedora = repoContas.findById(transacaoExistente.getIdRecebedora());

        if (contaEnviadora.isEmpty() || contaRecebedora.isEmpty()) {
            throw new TratamentoException("Contas envolvidas não encontradas");
        }

        ContaUsuarios enviadora = contaEnviadora.get();
        ContaUsuarios recebedora = contaRecebedora.get();


        enviadora.setSaldoInicial(enviadora.getSaldoInicial() + transacaoExistente.getValor());
        recebedora.setSaldoInicial(recebedora.getSaldoInicial() - transacaoExistente.getValor());

        repoContas.save(enviadora);
        repoContas.save(recebedora);


        repository.deleteById(transacaoId);

        return ResponseEntity.ok("Transação estornada com sucesso");
    }

 @Transactional
    public ResponseEntity listarTransacoesPorData(Long usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        List<Transacao> transacoes = repository.findAllByIdEnviadoraOrIdRecebedoraAndHoraDaTransacaoBetween(
                usuarioId, usuarioId, dataInicio, dataFim);

        return ResponseEntity.ok(transacoes);
    }


    public ResponseEntity visualizarSaldo(Long usuarioId) {
        var conta = repoContas.findById(usuarioId);

        if (conta.isEmpty()) {
            throw new TratamentoException("Conta não encontrada");
        }

        return ResponseEntity.ok(conta.get().getSaldoInicial());
    }
    }

