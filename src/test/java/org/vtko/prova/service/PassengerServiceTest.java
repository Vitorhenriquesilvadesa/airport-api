package org.vtko.prova.service;

import org.vtko.prova.model.Checkin;
import org.vtko.prova.model.Passenger;
import org.vtko.prova.repository.PassengerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepo;

    @InjectMocks
    private PassengerService passengerService;

    public PassengerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCheckInPassenger() {
        Passenger p = new Passenger();
        p.setId("p1");
        p.setFlightId("f1");

        when(passengerRepo.findById("p1")).thenReturn(Optional.of(p));
        Passenger result = passengerService.checkIn("p1");

        assertEquals(Checkin.Done, result.getStatusCheckIn());
    }
}
