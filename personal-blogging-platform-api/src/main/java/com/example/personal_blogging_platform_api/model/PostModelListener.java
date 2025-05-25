package com.example.personal_blogging_platform_api.model;

import com.example.personal_blogging_platform_api.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class PostModelListener extends AbstractMongoEventListener<BlogPost> {
    @Autowired
    private BlogPostRepository postRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BlogPost> event) {
        BlogPost post = event.getSource();

        // Generate slug only if it's not set
        if (post.getSlug() == null || post.getSlug().isEmpty()) {
            String generatedSlug = generateSlug(post.getTitle());
            post.setSlug(generatedSlug);
        }

        // Ensure unique slug
        post.setSlug(ensureSlugUniqueness(post.getSlug()));
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    private String ensureSlugUniqueness(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;

        while (postRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter++;
        }

        return slug;
    }
}