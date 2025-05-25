package com.example.personal_blogging_platform_api.service;

import com.example.personal_blogging_platform_api.dto.BlogUserPreviewDto;
import com.example.personal_blogging_platform_api.model.BlogUser;
import com.example.personal_blogging_platform_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BlogUserService {
    private final UserRepository userRepository;

    @Autowired
    public BlogUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Add new user
    @Transactional
    public BlogUser addUser(BlogUser user){
        BlogUser saveUser = userRepository.save(user);
        if(!user.getFollowing().isEmpty()){
            user.getFollowing().forEach(userId ->{
                BlogUser blogUser = userRepository.findById(userId).get();
                blogUser.getFollowers().add(saveUser.getUsername());
                userRepository.save(blogUser);
            });
        }
        return saveUser;
    }

    // Delete User
    public void deleteUserById(String userId){
        userRepository.deleteById(userId);
    }


    public List<BlogUserPreviewDto> fetchAllUsers() {
        return userRepository.findAll().stream().
                map(BlogUserPreviewDto::toUserPreviewDto).
                toList();
    }

    public List<BlogUser> fetchAllUserDetails(){
        return userRepository.findAll();
    }

    public void updateUserPassword(String username, String password) {
        BlogUser user = userRepository.findByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }
}
