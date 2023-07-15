package com.biblioteca.bibliotetca;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.biblioteca.bibliotetca.controller.PrestamoController;
import com.biblioteca.bibliotetca.models.Prestamo;
import com.biblioteca.bibliotetca.service.PrestamoService;

@ExtendWith(MockitoExtension.class)
public class PrestamoControllerTest {
    @Mock
    private PrestamoService prestamoService;

    @InjectMocks
    private PrestamoController prestamoController;

    private MockMvc mockMvc;

    @Test
    public void crearPrestamo_WithValidPrestamo_Returns200() throws Exception {
        // Arrange
        Prestamo prestamo = new Prestamo();
        prestamo.setIsbn("1234567890");
        prestamo.setFechaDevolucion(LocalDate.now());

        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();

        // Act & Assert
        mockMvc.perform(post("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isbn\":\"1234567890\",\"fechaDevolucion\":\"2023-07-13\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"fechaMaximaDevolucion\":\"13/07/2023\"}"));

        // Verify that the createPrestamo method in prestamoService is invoked
        verify(prestamoService).crearPrestamo(any(Prestamo.class));
    }

    @Test
    public void crearPrestamo_WithInvalidPrestamo_Returns400() throws Exception {
        // Arrange
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();

        // Act & Assert
        mockMvc.perform(post("/prestamo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isbn\":\"\",\"fechaDevolucion\":\"2023-07-13\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("Invalid prestamo"));

        // Verify that the createPrestamo method in prestamoService is not invoked
        verify(prestamoService, never()).crearPrestamo(any(Prestamo.class));
    }
}
