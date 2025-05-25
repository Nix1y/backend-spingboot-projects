package com.example.personal_blogging_platform_api.dto;

import com.example.personal_blogging_platform_api.model.BlogUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserPreviewDto {
    private String id;
    private String displayName;
    private String email;

    public static BlogUserPreviewDto toUserPreviewDto(BlogUser user) {
        return new BlogUserPreviewDto(user.getId(),user.getDisplayName(),user.getEmail());
    }
}
