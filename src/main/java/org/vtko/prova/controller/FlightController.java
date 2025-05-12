package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vtko.prova.dto.AssignGate;
import org.vtko.prova.dto.UpdateFlight;
import org.vtko.prova.dto.UpdateFlightStatus;
import org.vtko.prova.model.Flight;
import org.vtko.prova.repository.FlightRepository;
import org.vtko.prova.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepo;

    @Autowired
    private FlightService flightService;

    @PostMapping
    public Flight create(@RequestBody Flight flight) {
        Flight created = flightRepo.save(flight);
        flightService.assignGate(flight.getId(), flight.getGateId());
        return created;
    }

    @GetMapping
    public List<Flight> list() {
        return flightRepo.findAll();
    }

    @PatchMapping("")
    public Flight update(@RequestBody UpdateFlight updateFlight) {
        updateFlight.flight().setId(updateFlight.id());
        return flightRepo.save(updateFlight.flight());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        flightRepo.deleteById(id);
    }

    @PatchMapping("/status")
    public Flight updateStatus(@RequestBody UpdateFlightStatus updateStatus) {
        return flightService.updateStatus(updateStatus.flightId(), updateStatus.status());
    }

    @PatchMapping("/gate")
    public Flight assignGate(@RequestBody AssignGate assign) {
        return flightService.assignGate(assign.flightId(), assign.gateId());
    }
}
