package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.vtko.prova.model.Flight;
import org.vtko.prova.model.Passenger;
import org.vtko.prova.model.Status;
import org.vtko.prova.repository.FlightRepository;
import org.vtko.prova.repository.PassengerRepository;
import org.vtko.prova.service.PassengerService;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepo;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private FlightRepository flightRepo;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping
    public Passenger create(@RequestBody Passenger p) {
        if (passengerRepo.existsByCpf(p.getCpf())) {
            throw new RuntimeException("CPF already registered");
        }
        return passengerRepo.save(p);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping
    public List<Passenger> list() {
        return passengerRepo.findAll();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PatchMapping("/check-in")
    public Passenger checkIn(@RequestParam String id) {
        Passenger passenger = passengerRepo.findById(id).get();
        Flight flight = flightRepo.findById(passenger.getFlightId()).get();

        if (flight.getStatus() != Status.Boarding) {
            throw new RuntimeException("Flight is not in Boarding");
        }

        return passengerService.checkIn(id);
    }
}
