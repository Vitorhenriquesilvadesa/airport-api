package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vtko.prova.model.Passenger;
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

    @PostMapping
    public Passenger create(@RequestBody Passenger p) {
        if (passengerRepo.existsByCpf(p.getCpf())) {
            throw new RuntimeException("CPF already registered");
        }
        return passengerRepo.save(p);
    }

    @GetMapping
    public List<Passenger> list() {
        return passengerRepo.findAll();
    }

    @PatchMapping("/check-in")
    public Passenger checkIn(@RequestParam String id) {
        return passengerService.checkIn(id);
    }
}
