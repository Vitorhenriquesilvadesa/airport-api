package org.vtko.prova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vtko.prova.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findByEmail(String email);
}