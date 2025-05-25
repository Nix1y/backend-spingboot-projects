package com.example.personal_blogging_platform_api.model;
import com.example.personal_blogging_platform_api.model.enums.BlogVisibility;
import com.example.personal_blogging_platform_api.model.enums.Status;
import com.mongodb.lang.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "post")
@Data
public class BlogPost {
    @Id
    private String id;

    @NotBlank
    private String title;

    @Indexed(unique = true)
    private String slug;


    @TextIndexed
    private String content;

    private String excerpt;

    private Status status = Status.DRAFT;
    private BlogVisibility visibility;
    private String feature_image;

    @DBRef
    private BlogUser author;

    private List<String> categories = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    private List<String> likes = new ArrayList<>();
    private int viewCount;

    private LocalDate published_at;

    @CreatedDate
    private LocalDate created_at;

    @LastModifiedDate
    private LocalDate updated_at;
}
