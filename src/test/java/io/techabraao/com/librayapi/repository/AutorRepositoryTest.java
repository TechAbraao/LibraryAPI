package io.techabraao.com.librayapi.repository;

import io.techabraao.com.librayapi.models.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
		autor.setNome("Maria");
		autor.setNacionalidade("Brasileira");
		autor.setDataNascimento(LocalDate.of(1950, 1, 31));
		Autor autorSalvo = repository.save(autor);

		System.out.println("Quem salvou: " + autorSalvo);

    }

    @Test
    public void atualizarRegistroTeste() {
        UUID id = UUID.fromString("34368c20-b912-4247-823b-5f9039452387");

        Optional<Autor> result = repository.findById(id);
        if (result.isPresent()) {
            System.out.println("ACHOU: " + result.get());
            Autor autorEncontrado = result.get();


            // Save tanto salva um novo ou atualiza um existente. Se tiver o ID, ele atualiza, se não tiver, ele salva. Tudo
            // Gira em torno do ID (UUID)
            autorEncontrado.setNome("ABRAAA");
            repository.save(autorEncontrado);
        } else {
            System.out.println("N ACHO");
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }
}
