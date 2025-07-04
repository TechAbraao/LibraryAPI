package io.techabraao.com.librayapi.repository;

import io.techabraao.com.librayapi.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

}
