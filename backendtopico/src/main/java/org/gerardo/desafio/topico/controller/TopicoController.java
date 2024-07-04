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

    //metodo para lisatr topico
    @GetMapping("/listar-topicos")
    public ResponseEntity<Page<DatosListaTopico>> listarTopicos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Topico> topicos = topicoRepository.findByActivoTrue(pageable);
        Page<DatosListaTopico> datosListaTopicoPage = topicos.map(DatosListaTopico::new);
        return ResponseEntity.ok(datosListaTopicoPage);
    }


    //metodo para ctualizar topico
    @PutMapping("/actualizar-topico")
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico=topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosActualizarTopico(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFechaCreacion(),
                topico.getStatus(),topico.getAutor(),topico.getCurso().getId()));
    }

    //metodo para elimiar topico

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity elimiarTopico(@PathVariable Long id){
        Topico topico=topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }


}

