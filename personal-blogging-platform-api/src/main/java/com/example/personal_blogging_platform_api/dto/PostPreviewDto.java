package com.example.personal_blogging_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPreviewDto {
    private String id;
    private String title;
    private String slug;

}
