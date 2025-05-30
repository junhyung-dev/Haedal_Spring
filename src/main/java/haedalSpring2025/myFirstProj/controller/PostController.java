package haedalSpring2025.myFirstProj.controller;

import haedalSpring2025.myFirstProj.domain.Post;
import haedalSpring2025.myFirstProj.domain.User;
import haedalSpring2025.myFirstProj.dto.PostResponseDto;
import haedalSpring2025.myFirstProj.service.AuthService;
import haedalSpring2025.myFirstProj.service.ImageService;
import haedalSpring2025.myFirstProj.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {
    private final AuthService authService;

    //private final LikeService likeService;
    private final ImageService imageService;
    private final PostService postService;

    @Autowired
    public PostController(AuthService authService, ImageService imageService, PostService postService) {
        this.authService = authService;
        this.imageService = imageService;
        this.postService = postService;
    }

    @PostMapping("/posts") //게시글 만들기
    public ResponseEntity<Void> createPost(@RequestParam("image") MultipartFile image, @RequestParam("content") String content,
                                           HttpServletRequest request) throws IOException {

        User currentUser = authService.getCurrentUser(request);
        String imageUrl = imageService.savePostImage(image);
        Post post = new Post(currentUser, content, imageUrl);
        postService.savePost(post);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/user/{userId}") //유저별 게시글 가져오기
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable Long userId) {
        List<PostResponseDto> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);

    }

}
