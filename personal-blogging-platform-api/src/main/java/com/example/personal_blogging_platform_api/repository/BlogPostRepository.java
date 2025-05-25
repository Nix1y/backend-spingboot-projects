package com.example.personal_blogging_platform_api.repository;

import com.example.personal_blogging_platform_api.dto.PostPreviewDto;
import com.example.personal_blogging_platform_api.model.BlogPost;
import com.example.personal_blogging_platform_api.model.BlogUser;
import com.example.personal_blogging_platform_api.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost,String> {

    @Query(value = "{'tags':?0}", fields = "{'title':1,'slug':1, 'excerpt':1}")
    List<BlogPost> findPostPreviewsByTag(String tag);

    List<BlogPost> findByAuthorAndStatus(BlogUser user, Status status, Pageable pageable);

    @Query(value = "{}",fields = "{'title':1, 'slug':1}")
    Page<PostPreviewDto> findPostPreviews(Pageable pageable);

    boolean existsBySlug(String slug);
}
