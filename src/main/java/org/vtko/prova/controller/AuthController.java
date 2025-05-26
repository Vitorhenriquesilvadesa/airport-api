package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vtko.prova.dto.EmployeeDTO;
import org.vtko.prova.dto.LoginDTO;
import org.vtko.prova.service.EmployeeService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EmployeeDTO dto) {
        service.register(dto);
        return ResponseEntity.ok("Funcionário cadastrado");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO dto) {

        String token = service.login(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }
}