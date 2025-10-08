package com.curso.springboot.cursoSpringboot.dao;

import com.curso.springboot.cursoSpringboot.dto.UsuarioRequestDTO;
import com.curso.springboot.cursoSpringboot.dto.UsuarioResponseDTO;
import com.curso.springboot.cursoSpringboot.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioDao {

    List<UsuarioResponseDTO> getUsuarios();
    void eliminarUsuario(Long id);
    UsuarioResponseDTO insertarUsuario(UsuarioRequestDTO usuario);
    Optional<UsuarioResponseDTO> actualizarUsuario(long id, UsuarioRequestDTO usuario);
    UsuarioResponseDTO login(UsuarioRequestDTO user);
}
