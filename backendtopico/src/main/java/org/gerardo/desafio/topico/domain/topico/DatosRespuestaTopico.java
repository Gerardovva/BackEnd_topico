package org.gerardo.desafio.topico.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
       String titulo,
         String mensaje,
         LocalDateTime fechaCreacion,
        String status,
       String autor) {


    // Constructor
    public DatosRespuestaTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String status, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
        this.autor = autor;
    }


}
