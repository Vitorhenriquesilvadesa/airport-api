package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.vtko.prova.dto.AssignGate;
import org.vtko.prova.dto.FlightDTO;
import org.vtko.prova.dto.UpdateFlight;
import org.vtko.prova.dto.UpdateFlightStatus;
import org.vtko.prova.model.Flight;
import org.vtko.prova.model.Gate;
import org.vtko.prova.repository.FlightRepository;
import org.vtko.prova.service.FlightService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepo;

    @Autowired
    private FlightService flightService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Flight create(@RequestBody Flight flight) {
        Flight created = flightRepo.save(flight);
        flightService.assignGate(flight.getId(), flight.getGateId());
        return created;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping
    public List<Flight> list() {
        return flightRepo.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping
    public ResponseEntity<?> update(@RequestBody UpdateFlight updateFlight) {
        FlightDTO dto = updateFlight.flight();
        Optional<Flight> flightOptional = flightRepo.findById(updateFlight.id());
        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();
            if (dto.departureTime() != null) {
                flight.setDepartureTime(dto.departureTime());
            }
            if (dto.destination() != null) {
                flight.setDestination(dto.destination());
            }
            if (dto.flightNumber() != null) {
                flight.setFlightNumber(dto.flightNumber());
            }
            if (dto.gateId() != null) {
                flightService.assignGate(flight.getId(), dto.gateId());
            }

            return ResponseEntity.ok(flightRepo.save(flight));
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        flightRepo.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PatchMapping("/status")
    public Flight updateStatus(@RequestBody UpdateFlightStatus updateStatus) {
        return flightService.updateStatus(updateStatus.flightId(), updateStatus.status());
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PatchMapping("/gate")
    public Flight assignGate(@RequestBody AssignGate assign) {
        return flightService.assignGate(assign.flightId(), assign.gateId());
    }
}
