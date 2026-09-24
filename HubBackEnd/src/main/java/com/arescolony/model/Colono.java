package com.arescolony.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // Le dice a la base de datos que cree una tabla con esta estructura
@Data   // Genera automáticamente los Getters, Setters y constructores gracias a Lombok
public class Colono {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String rol; // Ejemplo: INGENIERO, CIENTIFICO, COMANDANTE
    private int nivelOxigenoTraje; // ¡Aquí conectaremos el proyecto anterior!
}