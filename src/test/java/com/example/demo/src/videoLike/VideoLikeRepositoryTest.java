package com.example.demo.src.videoLike;

import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.model.User;
import com.example.demo.src.video.VideoRepository;
import com.example.demo.src.video.dto.VideoResDto;
import com.example.demo.src.video.model.Video;
import com.example.demo.src.videoLike.dto.TopLikeVideoResDto;
import com.example.demo.src.videoLike.model.VideoLike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class VideoLikeRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    VideoLikeRepository videoLikeRepository;

    @Test
    public void videoLike검사() {

        User user1 = new User("user1");
        User user2 = new User("user2");
        userRepository.save(user1);
        userRepository.save(user2);

        Video video1 = new Video("title1", "des", user1);
        Video video2 = new Video("title2", "des", user2);
        Video video3 = new Video("title3", "des", user1);
        Video video4 = new Video("title4", "des", user2);

        videoRepository.save(video1);
        videoRepository.save(video2);
        videoRepository.save(video3);
        videoRepository.save(video4);


        videoLikeRepository.save(new VideoLike(user1, video3));
        videoLikeRepository.save(new VideoLike(user2, video1));
        videoLikeRepository.save(new VideoLike(user1, video1));
        videoLikeRepository.save(new VideoLike(user2, video3));
        videoLikeRepository.save(new VideoLike(user1, video3));
        videoLikeRepository.save(new VideoLike(user2, video4));
        videoLikeRepository.save(new VideoLike(user1, video1));
        videoLikeRepository.save(new VideoLike(user2, video4));
        videoLikeRepository.save(new VideoLike(user2, video3));
        videoLikeRepository.save(new VideoLike(user2, video2));

    }

    @Test
    public void 좋아요순으로검사() {


        PageRequest pageRequest = PageRequest.of(0, 5);

        Page<VideoLike> page = videoLikeRepository.findVideoByLike(pageRequest);

        // 뭐냐 아예 객체 보내고 내부에서 처리하는 게 훨씬 낫겠다 너무 귀찮다
        Page<TopLikeVideoResDto> apiDto = page.map(vl -> new TopLikeVideoResDto(vl.getVideo().getId(),
                vl.getVideo().getThumnailImg(), vl.getVideo().getTitle(), vl.getVideo().getCreatedTime(), videoLikeRepository.findVideoLikesById(vl.getVideo().getId()), vl.getUser().getId(), vl.getUser().getName(), vl.getUser().getProfileImg()));

        List<VideoLike> content = page.getContent();

        

        for (VideoLike videoLike : content) {
            System.out.println("videoid : " + videoLike.getVideo().getId() + "video_count : " + videoLike.getVideo().getVideoLikes().size());

        }
    }

    @Test
    public void 비디오좋아요갯수검사() {

        int videoLikesById = videoLikeRepository.findVideoLikesById(17L);

        System.out.println("videoLikes = " + videoLikesById);

    }



}