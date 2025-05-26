package org.vtko.prova.dto;


import org.vtko.prova.model.Status;
import java.time.LocalDateTime;

public record FlightDTO(String flightNumber,
                        String origin,
                        String destination,
                        LocalDateTime departureTime,
                        String gateId,
                        Status status) {
}
