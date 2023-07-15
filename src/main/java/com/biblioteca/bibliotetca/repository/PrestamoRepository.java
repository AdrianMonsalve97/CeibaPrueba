package com.biblioteca.bibliotetca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.bibliotetca.models.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Prestamo findFirstById(Long id);
    List<Prestamo> findByIdentificacionUsuario(String identificacionUsuario);
}
