package org.gerardo.desafio.topico.controller;

import jakarta.validation.Valid;
import org.gerardo.desafio.topico.domain.curso.Curso;
import org.gerardo.desafio.topico.domain.curso.CursoRepository;
import org.gerardo.desafio.topico.domain.topico.*;
import org.gerardo.desafio.topico.infra.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping("/crear-topico")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        // Primero, obtenemos el curso asociado al tópico
        Curso curso = cursoRepository.findById(datosRegistroTopico.idCurso())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + datosRegistroTopico.idCurso()));

        // Creamos una instancia de Topico usando el constructor que acepta DatosRegistroTopico y Curso
        Topico nuevoTopico = new Topico(datosRegistroTopico, curso);

        // Guardamos el nuevo tópico en la base de datos
        nuevoTopico = topicoRepository.save(nuevoTopico);

        // Creamos la respuesta de datos
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(nuevoTopico.getId(), nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(), nuevoTopico.getFechaCreacion(), nuevoTopico.getStatus(), nuevoTopico.getAutor());

        // Construimos la URL de la respuesta
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(nuevoTopico.getId()).toUri();

        // Retornamos una respuesta con estado CREATED (201) y la URL de la ubicación del nuevo recurso
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping("/listar-topico")
    public ResponseEntity<List<DatosListaTopico>> listaTopicos() {
        // Implementa la lógica para obtener una lista de tópicos y retornarla en una ResponseEntity
        List<Topico> topicos = topicoRepository.findAll();
        List<DatosListaTopico> datosListaTopicos = topicos.stream()
                .map(topico -> new DatosListaTopico(topico.getId(),topico.getMensaje(), topico.getTitulo(),topico.getFechaCreacion(), topico.getMensaje()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(datosListaTopicos);
    }
}
