package com.curso.springboot.cursoSpringboot.dao;

import com.curso.springboot.cursoSpringboot.dto.UsuarioRequestDTO;
import com.curso.springboot.cursoSpringboot.dto.UsuarioResponseDTO;
import com.curso.springboot.cursoSpringboot.mapper.UsuarioMapper;
import com.curso.springboot.cursoSpringboot.models.Usuario;
import com.curso.springboot.cursoSpringboot.repositories.UsuarioRepositorio;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioDaoImp implements UsuarioDao{

    @Autowired
    private UsuarioRepositorio repo;

    @Autowired
    private UsuarioMapper mapper;




    @Override
    public List<UsuarioResponseDTO> getUsuarios() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO insertarUsuario(UsuarioRequestDTO usuario) {
        Usuario user = mapper.toEntity(usuario);
        repo.save(user);
        return mapper.toResponse(user);
    }

    @Override
    public Optional<UsuarioResponseDTO> actualizarUsuario(long id, UsuarioRequestDTO usuario) {
        return repo.findById(id).map(existe->{
            existe.setNombre(usuario.getNombre());
            existe.setApellidos(usuario.getApellidos());
            repo.save(existe);
            return mapper.toResponse(existe);
        });
    }

    @Override
    public UsuarioResponseDTO login(UsuarioRequestDTO user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        List<Optional<Usuario>> usersMail = repo.findByEmail(user.getEmail());


        if(!usersMail.isEmpty()){
            System.out.println("Llego a entrar aqui");
            Usuario userLog = usersMail.getFirst().get();
            if (argon2.verify(userLog.getPassword(), user.getPassword())) {
                return mapper.toResponse(usersMail.getFirst().get()); // login exitoso
            }
        }

        throw new RuntimeException();

    }
}
