package com.arescolony;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.arescolony.model.Colono;
import com.arescolony.repository.ColonoRepository;

@SpringBootApplication
public class AresColonyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresColonyApplication.class, args);
    }

    @Bean
    public CommandLineRunner datosDePrueba(ColonoRepository repository) {
        return args -> {
            Colono comandante = new Colono();
            comandante.setNombre("Alex Vance");
            comandante.setRol("COMANDANTE");
            comandante.setNivelOxigenoTraje(100);

            repository.save(comandante);
            System.out.println("🚀 [ARES HUB]: Datos del Comandante cargados en la base de datos.");
        };
    }
}