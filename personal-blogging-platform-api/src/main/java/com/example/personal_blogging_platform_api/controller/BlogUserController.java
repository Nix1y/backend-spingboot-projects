package com.example.personal_blogging_platform_api.controller;

import com.example.personal_blogging_platform_api.dto.BlogUserPreviewDto;
import com.example.personal_blogging_platform_api.model.BlogUser;
import com.example.personal_blogging_platform_api.service.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class BlogUserController {
    @Autowired
    private BlogUserService userService;

    // Add new user
    @PostMapping
    public ResponseEntity<BlogUser> addUser(@RequestBody BlogUser user){
        BlogUser saveUser = userService.addUser(user);
        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    // Delete user by Id
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId){
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User Deleted Successfully ");
    }

    // Find All users using DTO to preview
    @GetMapping
    public ResponseEntity<List<BlogUserPreviewDto>> fetchAllUser(){
        List<BlogUserPreviewDto> blogUsers = userService.fetchAllUsers();
        return new ResponseEntity<>(blogUsers,HttpStatus.OK);
    }

    // Fetch all users with complete information
    @GetMapping("/allDetails")
    public ResponseEntity<List<BlogUser>> fetchAllUserDetails(){
        return new ResponseEntity<>(userService.fetchAllUserDetails(),HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestParam String username, @RequestParam String password){
        userService.updateUserPassword(username,password);
        return new ResponseEntity<>("password updated successful",HttpStatus.OK);
    }

}
