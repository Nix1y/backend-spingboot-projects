package com.example.personal_blogging_platform_api.controller;

import com.example.personal_blogging_platform_api.dto.PostPreviewDto;
import com.example.personal_blogging_platform_api.model.BlogPost;
import com.example.personal_blogging_platform_api.model.PostPreview;
import com.example.personal_blogging_platform_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogPostController {
    @Autowired
    private PostService postService;

    // Find All Posts
    @GetMapping
    public List<BlogPost> getPosts(){
        return postService.fetchAllPosts();
    }

    //Find Post based on ID
    @GetMapping("/{id}")
    public BlogPost getPostByID(@PathVariable("id") String id){
        return postService.findPostById(id);
    }

    // Update post based on ID
    @PutMapping("/{id}")
    public BlogPost updatePostById(@PathVariable("id") String id, @RequestBody BlogPost post){
        return postService.updateArticle(post,id);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") String id){
        postService.deleteArticle(id);
        return ResponseEntity.ok("Post deleted successfully ");
    }

    // Get all post using post preview
    @GetMapping("/posts")
    public Page<PostPreviewDto> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return postService.findAllPosts(page,size);
    }

    // Create the post
    @PostMapping
    public ResponseEntity<BlogPost> addPost(@RequestBody BlogPost post){
        BlogPost blogPost = postService.createPost(post);
        return new ResponseEntity<>(blogPost, HttpStatus.OK);
    }
}
