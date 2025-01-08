package com.Zerphyis.strat.contasUsuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.LongFunction;

@Repository
public interface RepositoryContaUsuarios extends JpaRepository<ContaUsuarios , Long > {
}
