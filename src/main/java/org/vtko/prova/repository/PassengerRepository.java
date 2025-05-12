package org.vtko.prova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.vtko.prova.model.Flight;
import org.vtko.prova.model.Passenger;
import java.util.List;

@Repository
public interface PassengerRepository extends MongoRepository<Passenger, String> {
    boolean existsByCpf(String cpf);
    List<Passenger> findAllByFlightId(String flightId);
}