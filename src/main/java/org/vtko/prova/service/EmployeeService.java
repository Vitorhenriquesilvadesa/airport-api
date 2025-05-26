package org.vtko.prova.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vtko.prova.dto.EmployeeDTO;
import org.vtko.prova.dto.LoginDTO;
import org.vtko.prova.model.Employee;
import org.vtko.prova.repository.EmployeeRepository;
import org.vtko.prova.security.JwtUtil;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(EmployeeDTO dto) {
        if (repo.findByEmail(dto.email).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Employee employee = new Employee();
        employee.setName(dto.name);
        employee.setEmail(dto.email);
        employee.setPassword(encoder.encode(dto.password));
        employee.setRole(dto.role);

        repo.save(employee);
    }

    public String login(LoginDTO dto) {
        Employee employee = repo.findByEmail(dto.email)
                .orElseThrow(() -> new RuntimeException("Email inválido"));

        if (!encoder.matches(dto.password, employee.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtUtil.generateToken(employee);
    }
}