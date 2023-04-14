package com.instagram.project.Controller;

import com.instagram.project.Domain.Post;
import com.instagram.project.Domain.User;
import com.instagram.project.Dto.PostDto;
import com.instagram.project.Dto.UserDto;
import com.instagram.project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/posts")
    public PostDto createPost(@RequestBody PostDto postDto) {
        Post post = new Post();
        post.setCaption(postDto.getCaption());
        post.setImageUrl(postDto.getImageUrl());
        post.setLikesCount(postDto.getLikesCount());
        post.setCommentsCount(postDto.getCommentsCount());
        if (postDto.getUser() != null) {
            User user = new User();
            user.setId(postDto.getUser().getId());
            user.setName(postDto.getUser().getName());
            user.setEmail(postDto.getUser().getEmail());
            user.setPassword(postDto.getUser().getPassword());
            post.setUser(user);
        }

        Post createdPost = userService.createPost(post);

        PostDto createdPostDto = new PostDto();
        createdPostDto.setId(createdPost.getId());
        createdPostDto.setCaption(createdPost.getCaption());
        createdPostDto.setImageUrl(createdPost.getImageUrl());
        createdPostDto.setLikesCount(createdPost.getLikesCount());
        createdPostDto.setCommentsCount(createdPost.getCommentsCount());
        createdPostDto.setUsersLiked(createdPost.getUsersLiked());
        createdPostDto.setUserName(createdPost.getUser().getName());
        createdPostDto.setUser(new UserDto(createdPost.getUser().getId(), createdPost.getUser().getName(), createdPost.getUser().getEmail(), createdPost.getUser().getPassword()));

        return createdPostDto;
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.createUser(userDto);
        if (createdUserDto != null) {
            return ResponseEntity.ok(createdUserDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("post")
    public ResponseEntity<Post> updatePost(@RequestBody PostDto postDto){
         Post updatedPost = userService.updatePost(postDto);
         if (updatedPost != null){
             return ResponseEntity.ok(updatedPost);
         }
         else {
             return ResponseEntity.notFound().build();
         }
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        userService.deletePost(postId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        userService.likePost(postId, userId);
        return ResponseEntity.ok("Post liked successfully");
    }
    @PostMapping("/{postId}/unlike/{userId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        userService.unlikePost(postId, userId);
        return ResponseEntity.ok("post unliked successfully");
    }

    @PostMapping("/{postId}/comment/{userId}")
    public ResponseEntity<String> addComment(@PathVariable Long postId, @RequestBody String comment, @PathVariable Long userId) {
        userService.addComment(postId, comment, userId);
        return ResponseEntity.ok("Comment is added to the post");
    }


    @PutMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Long userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        if (updatedUser != null) {
            return updatedUser;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
    }
    @GetMapping("/getPosts")
    public ResponseEntity<List<PostDto>> getPosts(){
        List<PostDto> posts = userService.getAllPost();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
    }



}
