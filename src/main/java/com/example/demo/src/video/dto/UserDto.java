package com.example.demo.src.video.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private String profileImg;

    public UserDto(Long id, String userName, String profileImg) {
        this.id = id;
        this.userName = userName;
        this.profileImg = profileImg;
    }
}
