package org.vtko.prova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.vtko.prova.model.Gate;

import java.util.Optional;

@Repository
public interface GateRepository extends MongoRepository<Gate, String> {
    Optional<Gate> findFirstByAvailableTrue();
}