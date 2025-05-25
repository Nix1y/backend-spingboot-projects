package com.example.personal_blogging_platform_api.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comments")
@Data
public class BlogComment {
    @Id
    private String id;

    @DBRef
    private BlogPost post;

    @DBRef
    private BlogUser user;

    @TextIndexed
    private String content;

    private String parentCommentId;
    private List<String> likes = new ArrayList<>();

    @CreatedDate
    private LocalDate createdAt;
}
