package io.techabraao.com.librayapi.dto;

import io.techabraao.com.librayapi.models.Autor;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
) {

    // Aqui configura o AutorDTO transformando em um objeto Autor
    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);

        return autor;
    }

}
