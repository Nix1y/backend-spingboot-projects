package com.example.personal_blogging_platform_api.service;

import com.example.personal_blogging_platform_api.dto.LoginDto;
import com.example.personal_blogging_platform_api.model.BlogUser;
import com.example.personal_blogging_platform_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean login(LoginDto login){
        BlogUser user = userRepository.findByUsername(login.getUsername());
        return user.getPassword().equals(login.getPassword());
    }
}
