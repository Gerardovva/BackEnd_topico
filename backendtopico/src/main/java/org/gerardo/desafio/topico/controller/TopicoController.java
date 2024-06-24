package org.gerardo.desafio.topico.controller;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.gerardo.desafio.topico.domain.curso.Curso;
import org.gerardo.desafio.topico.domain.curso.CursoRepository;
import org.gerardo.desafio.topico.domain.topico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;



@RestController
@RequestMapping("/topicos") // Agregar la ruta base para todos los endpoints en este controlador
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository; // Asumiendo que tienes un repositorio para Curso

    @PostMapping("/crear-topico")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        Curso curso = cursoRepository.findById(datosRegistroTopico.curso().getId())
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + datosRegistroTopico.curso().getId()));

        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, curso));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus().toString(),
                topico.getAutor()
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<DatosListaTopico> listaTopicos(){
        return null;
    }
}
