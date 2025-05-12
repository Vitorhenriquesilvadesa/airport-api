package org.vtko.prova.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "passengers")
public class Passenger {
    @Id
    private String id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Pattern(regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$", message = "Invalid CPF format")
    @Indexed(unique = true)
    private String cpf;

    @NotBlank(message = "Flight ID cannot be blank")
    private String flightId;

    @Enumerated(EnumType.STRING)
    private Checkin statusCheckIn;

    public Passenger() {
    }

    public Passenger(String id, String name, String cpf, String flightId, Checkin statusCheckIn) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.flightId = flightId;
        this.statusCheckIn = statusCheckIn;
    }
}
