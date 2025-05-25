package com.example.personal_blogging_platform_api.service;

import com.example.personal_blogging_platform_api.dto.PostPreviewDto;
import com.example.personal_blogging_platform_api.exception.PostNotFoundException;
import com.example.personal_blogging_platform_api.model.BlogPost;
import com.example.personal_blogging_platform_api.model.BlogUser;

import com.example.personal_blogging_platform_api.model.enums.Status;
import com.example.personal_blogging_platform_api.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    // Get All articles
    public List<BlogPost> fetchAllPosts(){
        return blogPostRepository.findAll();
    }

    // Get all articles for the specific filter
    public List<BlogPost> findAllPostWithTags(String tag){
        return blogPostRepository.findPostPreviewsByTag(tag);
    }
    // Get all articles by author
    public List<BlogPost> findByAuthorAndStatus(BlogUser user, Status status, Pageable pageable){
        return blogPostRepository.findByAuthorAndStatus(user,status,pageable);
    }

    // Get article based on article ID
    public BlogPost findPostById(String postId){
        return blogPostRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("Post not found with postID : "+postId));
    }

    // Update the article based on article ID
    public BlogPost updateArticle(BlogPost updatePost,String postId){
        BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("post not found with post ID : "+postId));
        blogPost.setAuthor(updatePost.getAuthor());
        blogPost.setContent(updatePost.getContent());
        blogPost.setExcerpt(updatePost.getExcerpt());
        blogPost.setFeature_image(updatePost.getFeature_image());
        blogPost.setTitle(updatePost.getTitle());
        return blogPostRepository.save(blogPost);
    }

    // Delete an article based on article ID
    public void deleteArticle(String postId){
        if(blogPostRepository.existsById(postId))
            blogPostRepository.deleteById(postId);
        else throw new PostNotFoundException("Post not found with post ID: "+ postId);
    }

    public Page<PostPreviewDto> findAllPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return blogPostRepository.findPostPreviews(pageRequest);
    }

    public BlogPost createPost(BlogPost post) {
        /*TODO
        *  Add the validation for the necessary fields like title, author
        * Add logic to add created at, published at, and updated at time
        *
        * */
        return blogPostRepository.save(post);
    }
}
