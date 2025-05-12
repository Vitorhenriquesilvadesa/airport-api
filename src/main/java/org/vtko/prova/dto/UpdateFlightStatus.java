package org.vtko.prova.dto;

import org.vtko.prova.model.Status;

public record UpdateFlightStatus(String flightId, Status status) {
}
