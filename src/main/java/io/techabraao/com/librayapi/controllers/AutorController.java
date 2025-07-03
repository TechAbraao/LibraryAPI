package io.techabraao.com.librayapi.controllers;

import io.techabraao.com.librayapi.dto.AutorDTO;
import io.techabraao.com.librayapi.models.Autor;
import io.techabraao.com.librayapi.services.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {

    // Service do Autor, onde encapsula a lógica do negócio
    AutorService autorService;

    // Injeção de Dependencia aqui no Controller
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping()
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor) {
        // ORGANIZADO
        Autor novoAutor = autor.mapearParaAutor();
        autorService.salvar(novoAutor);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoAutor.getId())
                .toUri();


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", autor))
                .created(location)
                .build();
    };

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhesPeloId(@PathVariable("id") String id) {
        UUID idAutor = UUID
                .fromString(id);

        Optional<Autor> autorObtido = autorService.obterPorId(idAutor);
        if (autorObtido.isPresent()) {
            Autor entidade = autorObtido.get();
            AutorDTO dto = new AutorDTO(entidade.getNome(), entidade.getDataNascimento(), entidade.getNacionalidade());

            return ResponseEntity
                    .ok(dto);

        }
        return ResponseEntity
                .notFound()
                .build(); // O Build finaliza a construção da Response sem corpo.
    }
}
