package com.example.demo.src.videoLike.dto;

import com.example.demo.src.video.dto.UserDto;
import com.example.demo.src.videoLike.model.VideoLike;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Data
public class TopLikeVideoResDto {

    private Long videoid;
    private String videoImg;
    private String videoTitle;
    private int videoLength;

    private LocalDateTime createdAt;

    private int views;
    private int likes;
    private String tag;

    private UserDto userDto;


    public TopLikeVideoResDto(VideoLike vl, int likes) {
        this.videoid = vl.getVideo().getId();
        this.videoImg = vl.getVideo().getThumnailImg();
        this.videoTitle = vl.getVideo().getTitle();
        this.createdAt = vl.getVideo().getCreatedTime();
        this.likes = likes;

        this.userDto = new UserDto(vl.getUser().getId(), vl.getUser().getName(), vl.getUser().getProfileImg());

    }

    public TopLikeVideoResDto(Long videoid, String videoImg, String videoTitle, LocalDateTime createdAt, int likes, Long userId, String userName, String profileImg) {

        this.videoid = videoid;
        this.videoImg = videoImg;
        this.videoTitle = videoTitle;
        this.createdAt = createdAt;
        this.likes = likes;
        this.userDto = new UserDto(userId, userName, profileImg);

    }

    public void userAdd(Long id, String userName) {
        this.userDto.setId(id);
        this.userDto.setUserName(userName);
    }
}
