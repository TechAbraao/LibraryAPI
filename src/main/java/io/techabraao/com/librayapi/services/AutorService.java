package io.techabraao.com.librayapi.services;

import io.techabraao.com.librayapi.models.Autor;
import io.techabraao.com.librayapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    // Repository do Autor, ou seja, comunica-se com o Banco de Dados
    AutorRepository autorRepository;

    // Injeção de dependência
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }


    // salvar no bd
    public Boolean salvar(Autor autor) {
        autorRepository.save(autor);
        return Boolean.TRUE;
    }

    // Atualizar
    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("É necessário que o autor já esteja no bd.");
        };
        autorRepository.save(autor);
    }

    // Deletar no BD
    public void deletarPorId(UUID id) {
       autorRepository.deleteById(id);
    }

    // buscar por id no bd
    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    // Buscar nome e nacionalidade
    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null) {
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }
}
