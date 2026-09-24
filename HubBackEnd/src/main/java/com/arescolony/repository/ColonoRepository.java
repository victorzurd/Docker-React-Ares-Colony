package com.arescolony.repository;

import com.arescolony.model.Colono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository ya viene con todos los métodos listos: guardar, borrar, buscar por ID, etc.
public interface ColonoRepository extends JpaRepository<Colono, Long> {
}