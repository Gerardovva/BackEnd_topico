package org.gerardo.desafio.topico.controller;
import jakarta.validation.Valid;
import org.gerardo.desafio.topico.domain.curso.Curso;
import org.gerardo.desafio.topico.domain.curso.CursoRepository;
import org.gerardo.desafio.topico.domain.topico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository; // Asumiendo que tienes un repositorio para Curso

    @PostMapping("/crear-topico")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        // Asumiendo que datosRegistroTopico no tiene un campo curso directamente, sino que se maneja de otra manera


        // Crear un nuevo Topico
        Topico topico = new Topico();
        topico.setTitulo(datosRegistroTopico.titulo());
        topico.setMensaje(datosRegistroTopico.mensaje());
        topico.setFechaCreacion(datosRegistroTopico.fechaCreacion() != null ? datosRegistroTopico.fechaCreacion() : LocalDateTime.now());
        topico.setStatus(datosRegistroTopico.status());
        topico.setAutor(datosRegistroTopico.autor());


        // Guardar el nuevo tópico en la base de datos
        Topico nuevoTopico = topicoRepository.save(topico);

        // Preparar la respuesta con los datos del nuevo tópico creado
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                nuevoTopico.getId(),
                nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(),
                nuevoTopico.getFechaCreacion(),
                nuevoTopico.getStatus(),
                nuevoTopico.getAutor()
        );

        // Construir la URL de respuesta
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(nuevoTopico.getId()).toUri();

        // Retornar una respuesta con el código HTTP 201 Created y los datos del nuevo tópico
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<DatosListaTopico> listaTopicos() {
        // Aquí implementa la lógica para obtener una lista de tópicos y retornarla en una ResponseEntity
        return null; // Por ahora se retorna null, deberías implementar esta lógica según tu necesidad
    }
}
