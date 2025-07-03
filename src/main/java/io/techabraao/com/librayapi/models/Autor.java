package io.techabraao.com.librayapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Deprecated
    public Autor(){}

    @Id
    @Column(name = "id",
            unique = true)
    @GeneratedValue(strategy = GenerationType.UUID) // UUID - Universal Unique ID
    private UUID id;

    // Mapeando e colocando as condições
    @Column(name = "nome",
            nullable = false,
            length = 100)
    private String nome;

    @Column(name = "data_nascimento",
            nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade",
            length = 50,
            nullable = false)
    private String nacionalidade;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;
}
