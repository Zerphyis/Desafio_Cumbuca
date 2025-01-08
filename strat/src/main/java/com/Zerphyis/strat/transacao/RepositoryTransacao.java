package com.Zerphyis.strat.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryTransacao extends JpaRepository<Transacao , Long> {
    List<Transacao> findAllByIdEnviadoraOrIdRecebedoraAndHoraDaTransacaoBetween(Long usuarioId, Long usuarioId1, LocalDate dataInicio, LocalDate dataFim);

/*
    @Query("SELECT t FROM Transacao t WHERE t.id = :id")
    Optional<Transacao> findById(Long id);
*/

}
