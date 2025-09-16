package com.curso.springboot.cursoSpringboot.controllers;

import com.curso.springboot.cursoSpringboot.dao.UsuarioDao;
import com.curso.springboot.cursoSpringboot.models.Usuario;
import com.curso.springboot.cursoSpringboot.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao userDao;

    @Autowired
    private JWTUtil jwtutil;

    @PostMapping("/api/login")
    public String iniciarSesion(@RequestBody Usuario user){

        Optional<Usuario> userLogin = userDao.login(user);
        if (userLogin.isPresent()) {
            System.out.println("El ID del usuario es: "+userLogin.get().getId());
            String tokenJWT = jwtutil.create(String.valueOf(userLogin.get().getId()),userLogin.get().getEmail());
            System.out.println("Token válido: "+tokenJWT);
            return tokenJWT;
        }
        System.out.println("Token no válido");
            return "NO";

    }

}
