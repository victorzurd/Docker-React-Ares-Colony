package com.arescolony.controller;

import java. util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.arescolony.model.Colono;
import com.arescolony.repository.ColonoRepository;

@RestController
@RequestMapping("/api/colonos") // Todos los endpoints empezarán por esta ruta
public class ColonoController {

    private final ColonoRepository colonoRepository;
    private final RestClient restClient;

    // Inyectamos el repositorio para poder usar la base de datos
    public ColonoController(ColonoRepository colonoRepository) {
        this.colonoRepository = colonoRepository;
        this.restClient = RestClient.builder()
            .baseUrl("http://cerebro-python:5000")
            .build();
    }

    // RUTA 1: Obtener todos los colonos (GET http://localhost:8080/api/colonos)
    @GetMapping
    public List<Colono> obtenerColonos() {
        return colonoRepository.findAll();
    }

    // RUTA 2: Registrar un nuevo colono (POST http://localhost:8080/api/colonos)
    @PostMapping
    public Colono registrarColono(@RequestBody Colono colono) {
        return colonoRepository.save(colono);
    }
}