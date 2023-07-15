package com.biblioteca.bibliotetca.controller;

import java.time.format.DateTimeFormatter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bibliotetca.models.Prestamo;
import com.biblioteca.bibliotetca.service.PrestamoService;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {
    private PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Object> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            prestamoService.crearPrestamo(prestamo);

            // Obtener el id del préstamo generado
            Long idPrestamo = prestamo.getId();

            // Obtener la fecha máxima de devolución en formato deseado
            String fechaMaximaDevolucion = prestamo.getFechaDevolucion()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Crear el objeto de respuesta JSON
            JsonObjectBuilder jsonResponseBuilder = Json.createObjectBuilder();
            jsonResponseBuilder.add("id", idPrestamo);
            jsonResponseBuilder.add("fechaMaximaDevolucion", fechaMaximaDevolucion);
            JsonObject jsonResponse = jsonResponseBuilder.build();

            // Devolver la respuesta HTTP 200 OK con el objeto de respuesta JSON
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse.toString());
        } catch (IllegalArgumentException e) {
            String mensaje = e.getMessage();

            // Crear el objeto de respuesta de error JSON
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("mensaje", mensaje)
                    .build();

            // Devolver la respuesta HTTP 400 Bad Request con el objeto de respuesta de error JSON
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse.toString());
        }
    }
}