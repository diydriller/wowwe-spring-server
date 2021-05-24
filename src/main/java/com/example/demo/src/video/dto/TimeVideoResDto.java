package com.example.demo.src.video.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeVideoResDto {

    private Long videoId;
    private String videoImg;
    private String videoTitle;
    private int videoLength;

    private LocalDateTime createdAt;

    private int views;
    private int likes;
    private String tag;

    private UserDto userDto;

    public TimeVideoResDto(Long videoid, String videoImg, String videoTitle, LocalDateTime createdAt, Long userId, String userName, String profileImg) {

        this.videoId = videoid;
        this.videoImg = videoImg;
        this.videoTitle = videoTitle;
        this.createdAt = createdAt;

        this.userDto = new UserDto(userId, userName, profileImg);

    }

    public void userAdd(Long id, String userName) {
        this.userDto.setId(id);
        this.userDto.setUserName(userName);
    }
}
