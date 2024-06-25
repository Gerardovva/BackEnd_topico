package org.gerardo.desafio.topico.domain.topico;

import java.time.LocalDateTime;

public class DatosRespuestaTopico {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor;

    // Constructor
    public DatosRespuestaTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String status, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
        this.autor = autor;
    }

    // Getters (y opcionalmente setters si es necesario)
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getStatus() {
        return status;
    }

    public String getAutor() {
        return autor;
    }
}
