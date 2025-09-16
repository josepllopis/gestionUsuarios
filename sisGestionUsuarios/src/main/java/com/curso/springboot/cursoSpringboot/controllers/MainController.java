package com.curso.springboot.cursoSpringboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String iniciar(){
        return "redirect:/usuarios.html";
    }
}
