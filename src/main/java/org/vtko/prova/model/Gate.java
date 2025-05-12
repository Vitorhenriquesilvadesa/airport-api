package org.vtko.prova.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "gates")
public class Gate {

    @Id
    private String id;

    @NotBlank(message = "Code cannot be blank")
    @Indexed(unique = true)
    @Pattern(regexp = "^[A-Z]\\d{1,2}$", message = "Code must follow the pattern (e.g., A1, B10, C99)")
    private String code; // e.g., "A5"

    private boolean available;
}
