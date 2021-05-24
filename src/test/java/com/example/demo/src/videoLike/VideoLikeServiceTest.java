package com.example.demo.src.videoLike;

import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.model.User;
import com.example.demo.src.video.VideoRepository;
import com.example.demo.src.video.VideoService;
import com.example.demo.src.video.dto.TimeVideoResDto;
import com.example.demo.src.video.model.Video;
import com.example.demo.src.videoLike.dto.TopLikeVideoResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class VideoLikeServiceTest {

    @Autowired
    VideoLikeService videoLikeService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoService videoService;

    @Test
    public void 비디오좋아요누르기() {

        long val = 1;

        videoLikeService.videoLike(val, val);

    }

    @Test
    public void top좋아요순으로받아오기() {

        Page<TopLikeVideoResDto> topLikeVideoPaging = videoLikeService.topLikeVideoPaging(0, 2);

        for (TopLikeVideoResDto topLikeVideoResDto : topLikeVideoPaging) {
            System.out.println("video : " + topLikeVideoResDto.getVideoTitle());

        }


    }

<<<<<<< HEAD
    @Test
    public void 최신영상받아오기() {
        Page<TimeVideoResDto> recentlyVideoPaging = videoService.recentlyVideoPaging(0, 3);


        for (TimeVideoResDto timeVideoResDto : recentlyVideoPaging) {
            System.out.println("result : " + timeVideoResDto.getVideoTitle());
        }

    }

=======


>>>>>>> 7c0660a74d5db9b2866564aa2c5497011da3ddcc
}