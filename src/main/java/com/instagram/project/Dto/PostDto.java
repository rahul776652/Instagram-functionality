package com.instagram.project.Dto;

import com.instagram.project.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String caption;
    private String imageUrl;
    private int likesCount;
    private List<String> comments;
    private int commentsCount;
    private List<User> usersLiked;
    private String userName;
    private UserDto user;



}
