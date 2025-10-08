package com.curso.springboot.cursoSpringboot.controllers;


import com.curso.springboot.cursoSpringboot.dao.UsuarioDao;
import com.curso.springboot.cursoSpringboot.dto.UsuarioRequestDTO;
import com.curso.springboot.cursoSpringboot.dto.UsuarioResponseDTO;
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



    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable int id, @RequestBody UsuarioRequestDTO usuario) {
        return userDao.actualizarUsuario(id, usuario)
                .map(ResponseEntity::ok)                  // 200 OK con el objeto actualizado
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found si no existe
    }



    @GetMapping("/listausuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> devolverUsuarios(@RequestHeader(value="Authorization")String token){
        List<UsuarioResponseDTO> usuarios = userDao.getUsuarios();
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
    public ResponseEntity<UsuarioResponseDTO> insertarUsuario(@RequestHeader(value="Authorization")String token, @RequestBody UsuarioRequestDTO user){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String hash = argon2.hash(1,1024,1,user.getPassword());
        user.setPassword(hash);
        UsuarioResponseDTO usuarioGuardado = userDao.insertarUsuario(user);

        String idUser = jwtUtil.getKey(token);
        System.out.println(token);
        if(idUser == null){
            System.out.println("No hay naaaa");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(usuarioGuardado,HttpStatus.CREATED);
    }


}
