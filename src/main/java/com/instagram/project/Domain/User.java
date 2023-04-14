package com.instagram.project.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Long> likedPostsIds;


    @ManyToMany(mappedBy = "usersLiked")
    private List<Post> likedPosts;


    public void setLikedPostsIds(List<Long> likedPostsIds) {
        this.likedPostsIds = likedPostsIds;
    }

    public List<Long> getLikedPostsIds() {
        return likedPostsIds;
    }

}
