package io.techabraao.com.librayapi.controllers;

import io.techabraao.com.librayapi.dto.AutorDTO;
import io.techabraao.com.librayapi.models.Autor;
import io.techabraao.com.librayapi.services.AutorService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<Map<String, List<AutorDTO>>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {

        List<Autor> lista = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> resultado = lista.stream().map(
                autor -> new AutorDTO(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                )
        ).collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(Map.of("message", resultado));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable String id) {
        UUID identifier = UUID.fromString(id);
        Optional<Autor> checkAutor = autorService.obterPorId(identifier);

        // Caso não encontre o UUID
        if (checkAutor.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "UUID não encontrado."));
        }
        // Caso encontre o UUID
        autorService.deletarPorId(identifier);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", "Deletado com sucesso."));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto) {
        UUID identifier = UUID.fromString(id);

        Optional<Autor> buscandoAutor = autorService.obterPorId(identifier);

        if (buscandoAutor.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Autor encontrouAutor = buscandoAutor.get();
        encontrouAutor.setNome(dto.nome());
        encontrouAutor.setDataNascimento(dto.dataNascimento());
        encontrouAutor.setNacionalidade(dto.nacionalidade());
        autorService.atualizar(encontrouAutor);

        return ResponseEntity
                .ok()
                .body(Map
                        .of("mensagem", "atualizado com sucesso."));
    }

    ;

}
