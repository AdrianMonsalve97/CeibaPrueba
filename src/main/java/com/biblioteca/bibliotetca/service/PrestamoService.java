package com.biblioteca.bibliotetca.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.bibliotetca.enums.EstadoPrestamo;
import com.biblioteca.bibliotetca.enums.TipoUsuarioEnum;
import com.biblioteca.bibliotetca.models.Prestamo;
import com.biblioteca.bibliotetca.repository.PrestamoRepository;

@Service
public class PrestamoService {
    private PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public void crearPrestamo(Prestamo prestamo) {
        // Validar que el usuario invitado solo tenga un libro prestado
        if (prestamo.getTipoUsuario() == TipoUsuarioEnum.INVITADO && tieneLibroPrestado(prestamo.getIdentificacionUsuario())) {
            throw new IllegalArgumentException("El usuario invitado ya tiene un libro prestado.");
        }

        // Validar el tipo de usuario permitido
        if (!esTipoUsuarioPermitido(prestamo.getTipoUsuario())) {
            throw new IllegalArgumentException("Tipo de usuario no permitido en la biblioteca.");
        }

        // Calcular la fecha máxima de devolución
        LocalDate fechaDevolucion = calcularFechaDevolucion(prestamo.getTipoUsuario());

        // Asignar la fecha de devolución y el estado al préstamo
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        // Guardar el préstamo en la base de datos
        prestamoRepository.save(prestamo);
    }

    private boolean tieneLibroPrestado(String identificacionUsuario) {
        List<Prestamo> prestamosUsuario = prestamoRepository.findByIdentificacionUsuario(identificacionUsuario);
        return prestamosUsuario.stream()
                .anyMatch(prestamo -> prestamo.getEstado() == EstadoPrestamo.ACTIVO);
    }

    private boolean esTipoUsuarioPermitido(TipoUsuarioEnum tipoUsuario) {
        return tipoUsuario == TipoUsuarioEnum.AFILIADO ||
               tipoUsuario == TipoUsuarioEnum.EMPLEADO_BIBLIOTECA ||
               tipoUsuario == TipoUsuarioEnum.INVITADO;
    }

    private LocalDate calcularFechaDevolucion(TipoUsuarioEnum tipoUsuario) {
        LocalDate fechaActual = LocalDate.now();

        switch (tipoUsuario) {
            case AFILIADO:
                fechaActual = sumarDiasHabiles(fechaActual, 10);
                break;
            case EMPLEADO_BIBLIOTECA:
                fechaActual = sumarDiasHabiles(fechaActual, 8);
                break;
            case INVITADO:
                fechaActual = sumarDiasHabiles(fechaActual, 7);
                break;
            default:
                throw new IllegalArgumentException("Tipo de usuario inválido.");
        }

        return fechaActual;
    }

    private LocalDate sumarDiasHabiles(LocalDate fecha, int dias) {
        int contador = 0;
        while (contador < dias) {
            fecha = fecha.plusDays(1);
            if (!(fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                contador++;
            }
        }
        return fecha;
    }
}
