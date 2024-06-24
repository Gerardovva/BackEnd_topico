package org.gerardo.desafio.topico.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.gerardo.desafio.topico.domain.curso.Curso;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotNull
        @NotBlank
        String titulo,

        @NotNull
        @NotBlank
        String mensaje,

        LocalDateTime fechaCreacion,

        @NotBlank
        String status,


        String autor,

        @NotNull
        Long idCurso



) {
}
