package org.vtko.prova.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GateTest {
    @Test
    public void testCodeValidation() {
        Gate gate = new Gate();
        gate.setCode("A5");

        // Aqui você pode usar um Validator do Bean Validation diretamente
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Gate>> violations = validator.validate(gate);

        // Verifique se há violações de validação
        assertTrue(violations.isEmpty());
    }
}
