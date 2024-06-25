package org.gerardo.desafio.topico.domain.topico;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

public record DatosListaTopico(
        Long id,
        String mensaje,
        String nombre,
        LocalDateTime fechaCreacion,
        String autor) {

    public DatosListaTopico(Topico topico){
        this(topico.getId(),topico.getMensaje(), topico.getTitulo(), topico.getFechaCreacion(), topico.getAutor());
    }
}
