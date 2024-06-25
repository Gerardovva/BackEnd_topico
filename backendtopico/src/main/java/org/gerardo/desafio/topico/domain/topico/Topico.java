package org.gerardo.desafio.topico.domain.topico;


import jakarta.persistence.*;
import lombok.*;
import org.gerardo.desafio.topico.domain.curso.Curso;


import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private Boolean activo;

    public Topico(DatosRegistroTopico datosRegistroTopico, Curso curso) {
        this.activo = true;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = datosRegistroTopico.fechaCreacion();
        this.status = datosRegistroTopico.status();
        this.autor = datosRegistroTopico.autor();
        this.curso = curso; // Asignar el curso proporcionado
    }

    //metodo para actualizar datos
    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.autor() != null) {
            this.autor = datosActualizarTopico.autor();
        }


    }

    public void desactivarTopico() {
        this.activo = false;
    }
}
