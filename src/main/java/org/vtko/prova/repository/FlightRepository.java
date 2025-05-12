package org.vtko.prova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.vtko.prova.model.Flight;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {}