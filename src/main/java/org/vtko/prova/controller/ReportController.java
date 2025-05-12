package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtko.prova.model.Gate;
import org.vtko.prova.repository.FlightRepository;
import org.vtko.prova.repository.GateRepository;
import org.vtko.prova.repository.PassengerRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private FlightRepository flightRepo;

    @Autowired
    private PassengerRepository passengerRepo;

    @Autowired
    private GateRepository gateRepo;

    @GetMapping
    public List<Map<String, Object>> getTodayReport() {
        LocalDate today = LocalDate.now();

        return flightRepo.findAll().stream()
                .filter(f -> f.getDepartureTime().toLocalDate().equals(today))
                .map(flight -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("flightNumber", flight.getFlightNumber());
                    data.put("status", flight.getStatus());

                    String gateCode = flight.getGateId() != null
                            ? gateRepo.findById(flight.getGateId()).map(Gate::getCode).orElse(null)
                            : null;
                    data.put("gate", gateCode);

                    var passengers = passengerRepo.findAllByFlightId(flight.getId())
                            .stream()
                            .map(p -> Map.of("cpf", p.getCpf(), "checkIn", p.getStatusCheckIn()))
                            .toList();

                    data.put("passengers", passengers);
                    return data;
                }).toList();
    }

    @GetMapping("/passengers")
    public List<Map<String, Object>> getPassengerListPerFlight() {
        return flightRepo.findAll().stream()
                .map(flight -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("flightNumber", flight.getFlightNumber());

                    var passengers = passengerRepo.findAllByFlightId(flight.getId())
                            .stream()
                            .map(p -> Map.of(
                                    "cpf", p.getCpf(),
                                    "checkIn", p.getStatusCheckIn()
                            ))
                            .toList();

                    data.put("passengers", passengers);
                    return data;
                }).toList();
    }

    @GetMapping("/gates")
    public List<Map<String, Object>> getFlightGateAssignments() {
        return flightRepo.findAll().stream()
                .map(flight -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("flightNumber", flight.getFlightNumber());
                    data.put("flightId", flight.getId());
                    data.put("status", flight.getStatus());

                    String gateCode = flight.getGateId() != null
                            ? gateRepo.findById(flight.getGateId())
                            .map(Gate::getCode)
                            .orElse("UNKNOWN")
                            : "UNASSIGNED";

                    data.put("gateCode", gateCode);
                    return data;
                }).toList();
    }


    @GetMapping("/checkin-status")
    public List<Map<String, Object>> getCheckInStatusReport() {
        return passengerRepo.findAll().stream()
                .map(passenger -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("cpf", passenger.getCpf());
                    data.put("checkIn", passenger.getStatusCheckIn());

                    // Recupera o número do voo correspondente, se existir
                    String flightNumber = flightRepo.findById(passenger.getFlightId())
                            .map(flight -> flight.getFlightNumber())
                            .orElse(null);

                    data.put("flightNumber", flightNumber);
                    return data;
                }).toList();
    }

}
