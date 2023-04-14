package com.instagram.project.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
        private Long id;
        private String name;
        private String email;
        private String password;
        private List<Long> likedPostsIds;

        public UserDto(Long id, String name, String email, String password) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.password = password;
        }


        public void setLikedPostsIds(List<Long> likedPostsIds) {
                this.likedPostsIds = likedPostsIds;
        }

        public List<Long> getLikedPostsIds() {
                return likedPostsIds;
        }

}