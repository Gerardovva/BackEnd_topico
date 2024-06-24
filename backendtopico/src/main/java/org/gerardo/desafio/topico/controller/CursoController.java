package org.gerardo.desafio.topico.controller;

import jakarta.validation.Valid;
import org.gerardo.desafio.topico.domain.curso.Curso;
import org.gerardo.desafio.topico.domain.curso.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // Endpoint para crear un nuevo curso
    @PostMapping("/crear-curso")
    public ResponseEntity<Curso> crearCurso(@RequestBody @Valid Curso nuevoCurso) {
        Curso cursoCreado = cursoRepository.save(nuevoCurso);

        URI uri = URI.create("/cursos/" + cursoCreado.getId()); // URI del recurso creado
        return ResponseEntity.created(uri).body(cursoCreado);
    }

    // Otros métodos según necesidades adicionales (listado de cursos, obtener curso por ID, etc.)

}
