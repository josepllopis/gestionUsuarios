package com.curso.springboot.cursoSpringboot.repositories;

import com.curso.springboot.cursoSpringboot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    List<Optional<Usuario>> findByEmail(String email);
}
