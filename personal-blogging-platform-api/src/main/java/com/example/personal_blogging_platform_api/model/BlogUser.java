package com.example.personal_blogging_platform_api.model;

import com.example.personal_blogging_platform_api.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Data
@Document(collection = "blogUser")
public class BlogUser {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String username;

    @Indexed(unique = true)
    @Email
    private String email;

    private String password;

    private String displayName;
    private String bio;
    private String avatar_url;

    @Field("role")
    private Role role = Role.AUTHOR;

    private Map<String,String> socialLinks;
    private List<String> followers = new ArrayList<>(); // List of user IDs.
    private List<String> following = new ArrayList<>();

    @CreatedDate
    private LocalDate created_at;

    @LastModifiedDate
    private LocalDate updated_at;
}
