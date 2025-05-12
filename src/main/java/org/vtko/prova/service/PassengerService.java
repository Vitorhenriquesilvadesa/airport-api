package org.vtko.prova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vtko.prova.model.Checkin;
import org.vtko.prova.model.Passenger;
import org.vtko.prova.repository.PassengerRepository;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepo;

    public Passenger checkIn(String passengerId) {
        Passenger p = passengerRepo.findById(passengerId).orElseThrow();

        if (p.getFlightId() == null) {
            throw new RuntimeException("Passenger is not assigned to a flight.");
        }

        p.setStatusCheckIn(Checkin.Done);
        return passengerRepo.save(p);
    }
}
