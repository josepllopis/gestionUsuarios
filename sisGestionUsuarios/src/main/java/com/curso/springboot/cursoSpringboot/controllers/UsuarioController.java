package com.curso.springboot.cursoSpringboot.controllers;


import com.curso.springboot.cursoSpringboot.dao.UsuarioDao;
import com.curso.springboot.cursoSpringboot.models.Usuario;
import com.curso.springboot.cursoSpringboot.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioDao userDao;

    @Autowired
    private JWTUtil jwtUtil;



    @GetMapping("/usuario/{id}")
    public Usuario getUsuario(@PathVariable String id){

        return new Usuario(Long.parseLong(id),"Lucas","Moy","josepllopis33@gmail.com","603825187");
    }



    @GetMapping("/listausuarios")
    public ResponseEntity<List<Usuario>> devolverUsuarios(@RequestHeader(value="Authorization")String token){
        List<Usuario> usuarios = userDao.getUsuarios();
        System.out.println("Estoy aquiiii 1");
        if(token==null){
            System.out.println("No existe");
        }else{
            System.out.println("Soy el token: "+token);
        }
        if(!validarToken(token)){
            System.out.println("No hay na");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(usuarios, HttpStatus.OK);

    }

    private boolean validarToken(String token){
        String idUser = jwtUtil.getKey(token);
        System.out.println(token);
        if(idUser == null){
            System.out.println("No hay naaaa");
            return false;
        }

        return true;
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id, @RequestHeader(value="Authorization")String token){
        String idUser = jwtUtil.getKey(token);

        if(idUser == null){
            System.out.println("No hay naaaa");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            userDao.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insertar")
    public ResponseEntity<Usuario> insertarUsuario(@RequestHeader(value="Authorization")String token, @RequestBody Usuario user){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String hash = argon2.hash(1,1024,1,user.getPassword());
        user.setPassword(hash);
        Usuario usuarioGuardado = userDao.insertarUsuario(user);

        String idUser = jwtUtil.getKey(token);
        System.out.println(token);
        if(idUser == null){
            System.out.println("No hay naaaa");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(usuarioGuardado,HttpStatus.CREATED);
    }


}
