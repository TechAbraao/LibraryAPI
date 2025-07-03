package io.techabraao.com.librayapi.repository;

import io.techabraao.com.librayapi.models.Autor;
import io.techabraao.com.librayapi.models.GeneroLivro;
import io.techabraao.com.librayapi.models.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("9430404");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro");
        livro.setData_publicacao(LocalDate.of(1980, 1, 20));

        Autor autor = autorRepository.getReferenceById(UUID.fromString("f4f7f5c5-4146-42a5-9f10-c06cfae36e95"));
        livro.setAutor(new Autor());

        repository.save(livro);

    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("9430404");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro");
        livro.setData_publicacao(LocalDate.of(1980, 1, 20));

        Autor autor = new Autor();
        autor.setNome("Abraao");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2002, 3, 8));

        // Autor autor = autorRepository.getReferenceById(UUID.fromString("f4f7f5c5-4146-42a5-9f10-c06cfae36e95"));
        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    void atualizarAutorDoLivro() {
        var livroPraAtualizar = repository
                .findById(UUID.fromString("79113a36-45a1-4687-8c0a-20ad87d5bd26"))
                .orElse(null).getId();

        UUID idAutor = UUID.fromString("f4f7f5c5-4146-42a5-9f10-c06cfae36e95");
        Autor autor = autorRepository.findById(idAutor).orElse(null);



    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("79113a36-45a1-4687-8c0a-20ad87d5bd26");
        Livro livro = repository.findById(id)
                .orElse(null);

        System.out.println("Nome do Autor: " +  livro.getAutor().getNome());
        System.out.println("Titulo do Livro: " + livro.getTitulo());

    }

}