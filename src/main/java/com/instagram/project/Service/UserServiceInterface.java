package com.instagram.project.Service;

import com.instagram.project.Domain.Post;
import com.instagram.project.Domain.User;
import com.instagram.project.Dto.PostDto;
import com.instagram.project.Dto.UserDto;

import java.util.List;

public interface UserServiceInterface {
    Post updatePost(PostDto postDto);
    void deletePost(Long postId);
    void deleteUser(Long userId);
    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    void addComment(Long postId, String comment, Long userId);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long id);
    List<PostDto> getAllPost();
    List<UserDto> getAllUser();

}
