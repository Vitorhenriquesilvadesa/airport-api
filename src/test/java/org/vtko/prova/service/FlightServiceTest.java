package org.vtko.prova.service;

import org.vtko.prova.model.Flight;
import org.vtko.prova.model.Gate;
import org.vtko.prova.repository.FlightRepository;
import org.vtko.prova.repository.GateRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepo;

    @Mock
    private GateRepository gateRepo;

    @InjectMocks
    private FlightService flightService;

    public FlightServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAssignGate() {
        Flight flight = new Flight();
        flight.setId("f1");

        Gate gate = new Gate();
        gate.setId("g1");
        gate.setAvailable(true);

        when(flightRepo.findById("f1")).thenReturn(Optional.of(flight));
        when(gateRepo.findById("g1")).thenReturn(Optional.of(gate));

        Flight result = flightService.assignGate("f1", "g1");
        assertEquals("g1", result.getGateId());
    }
}
