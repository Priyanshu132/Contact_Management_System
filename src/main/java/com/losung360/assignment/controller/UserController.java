package com.losung360.assignment.controller;

import com.losung360.assignment.responseDto.Response;
import com.losung360.assignment.util.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public ResponseEntity<Response> createToken(@RequestParam String userName,@RequestParam String password){


        if(userName.equalsIgnoreCase("Priyanshu") && password.equalsIgnoreCase("Gupta")){
            Jwt jwt = new Jwt();
            return new ResponseEntity<>(new Response(false,"Created",jwt.createJWT("1234")), HttpStatus.CREATED);
        }
        throw new RuntimeException("User Name OR Password is Incorrect !!!");
    }
}
