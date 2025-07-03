package io.techabraao.com.librayapi.repository;

import io.techabraao.com.librayapi.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {}
