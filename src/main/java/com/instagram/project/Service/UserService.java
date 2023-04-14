package com.instagram.project.Service;

import com.instagram.project.Domain.Post;
import com.instagram.project.Domain.User;
import com.instagram.project.Dto.PostDto;
import com.instagram.project.Dto.UserDto;
import com.instagram.project.Repository.PostRepository;
import com.instagram.project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPassword());
    }




    public Post createPost(Post postDto) {
        Post post = new Post();
        post.setCaption(postDto.getCaption());
        post.setImageUrl(postDto.getImageUrl());
        post.setLikesCount(0);
        post.setCommentsCount(0);
        User userDto = postDto.getUser();
        if (userDto != null && userDto.getId() == null) {
            User user = new User();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user = userRepository.save(user);
            post.setUser(user);
        }

        return postRepository.save(post);
    }


    public Post updatePost(PostDto postDto) {
        Post existingPost = postRepository.findById(postDto.getId()).orElse(null);
        if (existingPost != null) {
            existingPost.setCaption(postDto.getCaption());
            existingPost.setImageUrl(postDto.getImageUrl());
            existingPost.setLikesCount(postDto.getLikesCount());
            existingPost.setCommentsCount(postDto.getCommentsCount());
            return postRepository.save(existingPost);
        } else {
            return null;
        }
    }


    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

        public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (post != null && user != null) {
            if (!post.getUsersLiked().contains(user)) {
                post.getUsersLiked().add(user);
                post.setLikesCount(post.getLikesCount() + 1);
                postRepository.save(post);
            }
        }
    }


    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);

        User user = userRepository.findById(userId).orElse(null);

        if (post != null && user != null) {
            post.getUsersLiked().remove(user);

            postRepository.save(post);
        }
    }

        public void addComment(Long postId, String comment, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.getComments().add(comment);
            post.setCommentsCount(post.getCommentsCount() + 1);
            postRepository.save(post);
        }
    }

    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setLikedPostsIds(userDto.getLikedPostsIds());

            user = userRepository.save(user);

            return UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .likedPostsIds(user.getLikedPostsIds())
                    .build();
        } else {
            return null;
        }
    }

    public List<PostDto> getAllPost() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : postList) {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setComments(post.getComments());
            postDto.setUserName(post.getUser().getName());
            postDto.setCaption(post.getCaption());
            postDto.setImageUrl(post.getImageUrl());
            postDto.setLikesCount(post.getLikesCount());
            postDto.setCommentsCount(post.getCommentsCount());
            postDto.setUser(new UserDto(post.getUser().getId(), post.getUser().getName(), post.getUser().getEmail(), post.getUser().getPassword()));
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    public List<UserDto> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
