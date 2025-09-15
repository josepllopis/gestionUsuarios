package com.curso.springboot.cursoSpringboot.dao;

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




    @Override
    public List<Usuario> getUsuarios() {
        return repo.findAll();
    }

    @Override
    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Usuario insertarUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public Optional<Usuario> login(Usuario user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        List<Optional<Usuario>> usersMail = repo.findByEmail(user.getEmail());


        if(!usersMail.isEmpty()){
            Usuario userLog = usersMail.getFirst().get();
            if (argon2.verify(userLog.getPassword(), user.getPassword())) {
                return usersMail.getFirst(); // login exitoso
            }
        }


        return Optional.empty();

    }
}
