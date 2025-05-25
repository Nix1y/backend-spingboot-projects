package com.example.personal_blogging_platform_api.controller;

import com.example.personal_blogging_platform_api.dto.LoginDto;

import com.example.personal_blogging_platform_api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody LoginDto login){
        boolean response = loginService.login(login);
        if(response){
            return ResponseEntity.ok("Login Successful");
        }
        return new ResponseEntity<>("Either username or password is wrong", HttpStatus.NOT_ACCEPTABLE);
    }
}
