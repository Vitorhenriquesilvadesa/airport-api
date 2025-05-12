package org.vtko.prova.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    @NotBlank(message = "Flight number cannot be blank")
    @Pattern(regexp = "^[A-Z]{2}\\d{1,4}$", message = "Flight number must be in the format (e.g., AB123, XY99)")
    private String flightNumber;

    @NotBlank(message = "Origin cannot be blank")
    private String origin;

    @NotBlank(message = "Destination cannot be blank")
    private String destination;

    @FutureOrPresent(message = "Departure time must be in the future or present")
    private LocalDateTime departureTime;

    @NotBlank(message = "Gate ID cannot be blank")
    private String gateId; // refers to Gate

    @Enumerated(EnumType.STRING)
    private Status status; // "scheduled", "boarding", "finished"
}
