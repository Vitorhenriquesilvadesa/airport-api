package org.vtko.prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.vtko.prova.model.Gate;
import org.vtko.prova.repository.GateRepository;

import java.util.List;

@RestController
@RequestMapping("/gates")
public class GateController {

    @Autowired
    private GateRepository gateRepo;

    @PostMapping
    public Gate create(@RequestBody Gate gate) {
        return gateRepo.save(gate);
    }

    @GetMapping
    public List<Gate> list() {
        return gateRepo.findAll();
    }

    @PatchMapping("/{id}")
    public Gate update(@PathVariable String id, @RequestBody Gate gate) {
        gate.setId(id);
        return gateRepo.save(gate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        gateRepo.deleteById(id);
    }
}
