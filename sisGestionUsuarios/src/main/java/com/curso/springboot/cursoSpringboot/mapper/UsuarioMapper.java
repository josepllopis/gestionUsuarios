package com.curso.springboot.cursoSpringboot.mapper;

import com.curso.springboot.cursoSpringboot.dto.UsuarioRequestDTO;
import com.curso.springboot.cursoSpringboot.dto.UsuarioResponseDTO;
import com.curso.springboot.cursoSpringboot.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO){
        Usuario user = new Usuario();
        user.setNombre(usuarioRequestDTO.getNombre());
        user.setApellidos(usuarioRequestDTO.getApellidos());
        user.setEmail(usuarioRequestDTO.getEmail());
        user.setTelefono(usuarioRequestDTO.getTelefono());
        user.setPassword(usuarioRequestDTO.getPassword());

        return user;
    }

    public UsuarioResponseDTO toResponse(Usuario usuario){
        UsuarioResponseDTO user = new UsuarioResponseDTO();
        user.setNombre(usuario.getNombre());
        user.setApellidos(usuario.getApellidos());
        user.setEmail(usuario.getEmail());
        user.setTelefono(usuario.getTelefono());
        user.setId(usuario.getId());
        user.setPassword(usuario.getPassword());
        return user;
    }
}
