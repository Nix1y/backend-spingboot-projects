package com.example.personal_blogging_platform_api;

import com.example.personal_blogging_platform_api.exception.PostNotFoundException;
import com.example.personal_blogging_platform_api.model.BlogPost;
import com.example.personal_blogging_platform_api.model.BlogUser;
import com.example.personal_blogging_platform_api.repository.BlogPostRepository;

import com.example.personal_blogging_platform_api.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BlogUserServiceTest {

    @Autowired
    private PostService postService;

    @MockitoBean
    private BlogPostRepository postRepo;

    @Test
    public void testFetchAllPosts(){
        // Arrange mock for the repo
        BlogPost post1 = new BlogPost();
        post1.setTitle("testing");
        post1.setContent("Testing content");

        BlogPost post2 = new BlogPost();
        post2.setTitle("testing-2");
        post2.setContent("Testing content 2");

        List<BlogPost> listMock = Arrays.asList(post1,post2);
        when(postRepo.findAll()).thenReturn(listMock);

        // Call the service method
        List<BlogPost> result = postService.fetchAllPosts();

        // verify the result and interaction
        assertEquals(listMock,result);
        assertEquals("testing",result.get(0).getTitle());
        assertEquals("testing-2",result.get(1).getTitle());
        verify(postRepo,atMostOnce()).findAll();
    }

    @Test
    public void testFetchAllPost_whenNoRecord(){
        when(postRepo.findAll()).thenReturn(new ArrayList<>());

        // Call the service method
        List<BlogPost> result = postService.fetchAllPosts();

        // verify the result and interaction
        assertEquals(Collections.EMPTY_LIST,result);
        verify(postRepo,times(1)).findAll();
    }

    @Test
    public void testFindPostById(){
        // Arrange mock for the repo
        BlogPost post1 = mock(BlogPost.class);
        when(postRepo.findById(any())).thenReturn(Optional.of(post1));

        // Call the service method
        BlogPost result = postService.findPostById(any());

        // verify the result and interaction
        assertSame(post1,result);
        verify(postRepo,times(1)).findById(any());
    }

    @Test
    public void testFindPostById_throwsException(){
        String invalidId = "abc1234Test";
        when(postRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Call the service method
        PostNotFoundException exception = assertThrows(PostNotFoundException.class,
                ()->postService.findPostById(invalidId));

        // verify the result and interaction
        assertEquals("Post not found with postID : "+invalidId,exception.getMessage());
        verify(postRepo,times(1)).findById(invalidId);
    }

    @Test
    public void testUpdateArticle_CompleteExecution(){
        // Arrange mock for the repo
        String postId = "abc123Test";
        BlogPost post = new BlogPost();
        post.setAuthor(new BlogUser());
        post.setExcerpt("Old Excerpt");
        post.setFeature_image("def/test/abc/kle");
        post.setTitle("Old title");
        BlogUser mockAuthor = mock(BlogUser.class);
        BlogPost updatePost = mock(BlogPost.class);

        when(postRepo.findById(postId)).thenReturn(Optional.of(post));
        when(updatePost.getContent()).thenReturn("New Content");
        when(updatePost.getTitle()).thenReturn("New title");
        when(updatePost.getAuthor()).thenReturn(mockAuthor);
        when(updatePost.getExcerpt()).thenReturn("Excerpt");
        when(updatePost.getFeature_image()).thenReturn("abc/def/test");
        when(postRepo.save(any(BlogPost.class))).thenReturn(updatePost);

        // Call the service method
        BlogPost result = postService.updateArticle(updatePost,postId);

        // verify the result and interaction
        assertEquals("New title",result.getTitle());
        assertEquals("New Content",result.getContent());
        assertEquals("Excerpt",result.getExcerpt());
        assertEquals("abc/def/test",result.getFeature_image());
        assertSame(mockAuthor,result.getAuthor());
        verify(postRepo,times(1)).findById(any());
        verify(postRepo,times(1)).save(any(BlogPost.class));
    }

    @Test
    public void testUpdateArticle_throwsException(){
        String postId = "abc123Test";
        BlogPost updatePost = mock(BlogPost.class);
        when(postRepo.findById(postId)).thenReturn(Optional.empty());

        // Call the service method
        PostNotFoundException exception = assertThrows(PostNotFoundException.class,()->postService.updateArticle(updatePost,postId));

        // assert the result and interaction
        assertEquals("post not found with post ID : "+postId,exception.getMessage());
        verify(postRepo,times(1)).findById(postId);
    }

    @Test
    public void testDeleteArticle_SuccessfulExecution(){
        // Arrange db mock
        String postId = "abc123Test";
        BlogPost mockPost = mock(BlogPost.class);

        when(postRepo.existsById(postId)).thenReturn(true);

        // Call the service method
        postService.deleteArticle(postId);

        // verify the interaction
        verify(postRepo, times(1)).existsById(postId);
        verify(postRepo,times(1)).deleteById(postId);
    }

    @Test
    public void testDeleteArticle_throwsException(){
        // Arrange mock
        String postId = "abc123Test";

        when(postRepo.existsById(postId)).thenReturn(false);

        // Call the service method
        PostNotFoundException exception = assertThrows(PostNotFoundException.class,()->postService.deleteArticle(postId));

        // verify the interaction and result
        verify(postRepo,times(1)).existsById(postId);
        verify(postRepo,times(0)).deleteById(postId);
        assertEquals("Post not found with post ID: "+ postId,exception.getMessage());
    }

    @Test
    public void testCreatePost(){
        BlogPost mockPost = mock(BlogPost.class);

        when(postRepo.save(mockPost)).thenReturn(mockPost);

        // Call the service method
        BlogPost result = postService.createPost(mockPost);

        // assert the result and verify the interaction
        assertSame(result,mockPost);
        verify(postRepo,times(1)).save(any(BlogPost.class));
    }

    @Test
    public void testFindAllPostWithTags(){
        // Arrange mock
        String tag = "House";
        BlogPost post1 = mock(BlogPost.class);
        BlogPost post2 = mock(BlogPost.class);

        List<BlogPost> blogPosts = List.of(post1,post2);

        when(postRepo.findPostPreviewsByTag(tag)).thenReturn(blogPosts);
        when(blogPosts.get(0).getTags()).thenReturn(Collections.singletonList("House"));
        when(blogPosts.get(0).getTitle()).thenReturn("House tag testing");

        // call the service method
        List<BlogPost> result = postService.findAllPostWithTags(tag);

        // assert the result and interaction
        assertSame(blogPosts,result);
        assertEquals("House tag testing", result.get(0).getTitle());
        verify(postRepo,times(1)).findPostPreviewsByTag(tag);
    }

    @Test
    public void testFindAllPostWithTags_whenEmpty(){
        // Arrange mock
        String tag = "Invalid";

        when(postRepo.findPostPreviewsByTag(tag)).thenReturn(new ArrayList<>());

        // call the service method
        List<BlogPost> result = postService.findAllPostWithTags(tag);

        // assert the result and interaction
        assertEquals(Collections.EMPTY_LIST,result);
        verify(postRepo,times(1)).findPostPreviewsByTag(tag);

    }

}
