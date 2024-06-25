package org.gerardo.desafio.topico.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.gerardo.desafio.topico.domain.curso.Curso;
import org.gerardo.desafio.topico.domain.curso.CursoRepository;
import org.gerardo.desafio.topico.domain.topico.*;


import org.gerardo.desafio.topico.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    private final CursoRepository cursoRepository;
    private final TopicoRepository topicoRepository;

    @Autowired
    public TopicoController(CursoRepository cursoRepository, TopicoRepository topicoRepository) {
        this.cursoRepository = cursoRepository;
        this.topicoRepository = topicoRepository;
    }

    @PostMapping("/crear-topico")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@Valid @RequestBody DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        // Check if a topic with the same title and message already exists
        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            // Return a conflict response or throw an exception
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un tópico con el mismo título y mensaje");
        }

        // Proceed with saving the new topic
        Curso curso = cursoRepository.findById(datosRegistroTopico.idCurso())
                .orElseThrow(() -> new ValidacionDeIntegridad("Curso no encontrado con ID: " + datosRegistroTopico.idCurso()));

        Topico nuevoTopico = new Topico(datosRegistroTopico, curso);
        nuevoTopico.setFechaCreacion(LocalDateTime.now());
        nuevoTopico.setStatus("Activo");
        nuevoTopico.setActivo(true);

        nuevoTopico = topicoRepository.save(nuevoTopico);

        // Construct response data
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                nuevoTopico.getId(),
                nuevoTopico.getTitulo(),
                nuevoTopico.getMensaje(),
                nuevoTopico.getFechaCreacion(),
                nuevoTopico.getStatus(),
                nuevoTopico.getAutor()
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(nuevoTopico.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping("/listar-topicos")
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Topico> topicos = topicoRepository.findByActivoTrue(pageable);
        Page<DatosListaTopico> datosListaTopicoPage = topicos.map(DatosListaTopico::new);
        return ResponseEntity.ok(datosListaTopicoPage);
    }

    @PutMapping("/actualizar-topico/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id,
                                                                 @Valid @RequestBody DatosRegistroTopico datosRegistroTopico) {
        // Buscar el tópico a actualizar
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con ID: " + id));

        // Verificar si se está intentando cambiar el título o mensaje a uno ya existente
        if (!topico.getTitulo().equals(datosRegistroTopico.titulo())
                || !topico.getMensaje().equals(datosRegistroTopico.mensaje())) {
            if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        // Actualizar los datos del tópico
        topico.setTitulo(datosRegistroTopico.titulo());
        topico.setMensaje(datosRegistroTopico.mensaje());
        topico.setAutor(datosRegistroTopico.autor());

        // Guardar el tópico actualizado en la base de datos
        topico = topicoRepository.save(topico);

        // Construir la respuesta de datos
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor()
        );

        // Retornar una respuesta con estado OK (200) y los datos del tópico actualizado
        return ResponseEntity.ok(datosRespuestaTopico);
    }
}


   /*
    @GetMapping("/listar-topico")
    public ResponseEntity<List<DatosListaTopico>> listaTopicos() {
        // Implementa la lógica para obtener una lista de tópicos y retornarla en una ResponseEntity
        List<Topico> topicos = topicoRepository.findAll();
        List<DatosListaTopico> datosListaTopicos = topicos.stream()
                .map(topico -> new DatosListaTopico(topico.getId(),topico.getMensaje(), topico.getTitulo(),topico.getFechaCreacion(), topico.getMensaje()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(datosListaTopicos);
    } */

   /* @GetMapping("/listar-topico")
    public ResponseEntity<Page<DatosListaTopico>> listaTopicos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Topico> topicosPage = topicoRepository.findAll(pageable);

        // Mapear los tópicos de la página a DatosListaTopico
        Page<DatosListaTopico> datosListaTopicos = topicosPage.map(topico ->
                new DatosListaTopico(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensaje(),
                        topico.getFechaCreacion(),
                        topico.getAutor()
                )
        );

        return ResponseEntity.ok().body(datosListaTopicos);
    }*/




