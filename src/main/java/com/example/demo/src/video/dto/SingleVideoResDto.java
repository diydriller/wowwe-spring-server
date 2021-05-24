package com.example.demo.src.video.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SingleVideoResDto {

    private Long videoId;
    private String fileUrl;
    private String title;
    private String description;
    private boolean isLive;
    private String thumnailImg;
    private double duration;
    private LocalDateTime createdDate;

    private int likes;

    private UserDto userDto;

    public void add(Long userId, String userName, String profileImg) {
        this.userDto = new UserDto(userId, userName, profileImg);
    }
}