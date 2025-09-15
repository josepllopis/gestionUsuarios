package com.curso.springboot.cursoSpringboot.dao;

import com.curso.springboot.cursoSpringboot.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {

    List<Usuario> getUsuarios();
    void eliminarUsuario(Long id);
    Usuario insertarUsuario(Usuario usuario);

    Optional<Usuario> login(Usuario user);
}
