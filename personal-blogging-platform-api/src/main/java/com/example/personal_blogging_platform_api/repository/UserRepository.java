package com.example.personal_blogging_platform_api.repository;

import com.example.personal_blogging_platform_api.model.BlogUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<BlogUser, String> {

    BlogUser findByUsername(String username);
}
