package io.techabraao.com.librayapi.services;

import io.techabraao.com.librayapi.models.Autor;
import io.techabraao.com.librayapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

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

    // Deletar no BD
    public void deletarPorId(UUID id) {
       autorRepository.deleteById(id);
    }

    // buscar por id no bd
    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

}
