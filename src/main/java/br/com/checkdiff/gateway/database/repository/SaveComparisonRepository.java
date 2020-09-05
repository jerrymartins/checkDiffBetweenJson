package br.com.checkdiff.gateway.database.repository;

import br.com.checkdiff.gateway.database.entity.ComparisonResultEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveComparisonRepository extends ReactiveCrudRepository <ComparisonResultEntity, Long>  {}
