package com.biblioteca.bibliotetca.models;

import com.biblioteca.bibliotetca.enums.EstadoPrestamo;
import com.biblioteca.bibliotetca.enums.TipoUsuarioEnum;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prestamos")
@Data
public class Prestamo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String isbn;
  private String identificacionUsuario;
  private TipoUsuarioEnum tipoUsuario;
  private LocalDate fechaDevolucion;
  private EstadoPrestamo estado;

  public Prestamo() {}



  public Prestamo(Long id, String isbn, String identificacionUsuario, TipoUsuarioEnum tipoUsuario,
      LocalDate fechaDevolucion, EstadoPrestamo estado) {
    this.id = id;
    this.isbn = isbn;
    this.identificacionUsuario = identificacionUsuario;
    this.tipoUsuario = tipoUsuario;
    this.fechaDevolucion = fechaDevolucion;
    this.estado = estado;
  }
  



  public boolean isValid() {
    if (isbn == null || isbn.isEmpty() || isbn.length() > 10) {
      return false;
    }
    if (
      identificacionUsuario == null ||
      identificacionUsuario.isEmpty() ||
      identificacionUsuario.length() > 10
    ) {
      return false;
    }
    return true;
  }


}
