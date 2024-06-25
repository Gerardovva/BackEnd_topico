package org.gerardo.desafio.topico.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotNull
        @NotBlank
        String titulo,

        @NotNull
        @NotBlank
        String mensaje,

        @NotNull
        LocalDateTime fechaCreacion,

        @NotNull
        @NotBlank
        String status,

        @NotNull
        @NotBlank
        String autor,

        @NotNull
        Long idCurso
) {
}
