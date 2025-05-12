package org.vtko.prova.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vtko.prova.model.Flight;
import org.vtko.prova.model.Gate;
import org.vtko.prova.model.Status;
import org.vtko.prova.repository.FlightRepository;
import org.vtko.prova.repository.GateRepository;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepo;

    @Autowired
    private GateRepository gateRepo;

    public Flight assignGate(String flightId, String gateId) {
        Flight flight = flightRepo.findById(flightId).orElseThrow();
        Gate gate = gateRepo.findById(gateId).orElseThrow();

        if (!gate.isAvailable()) {
            throw new RuntimeException("Gate is not available.");
        }

        gate.setAvailable(false);
        gateRepo.save(gate);

        flight.setGateId(gate.getId());
        return flightRepo.save(flight);
    }

    public Flight updateStatus(String flightId, Status newStatus) {
        Flight flight = flightRepo.findById(flightId).orElseThrow();
        flight.setStatus(newStatus);

        if (newStatus == Status.Finished && flight.getGateId() != null) {
            Gate gate = gateRepo.findById(flight.getGateId()).orElseThrow();
            gate.setAvailable(true);
            gateRepo.save(gate);
        }

        return flightRepo.save(flight);
    }
}
