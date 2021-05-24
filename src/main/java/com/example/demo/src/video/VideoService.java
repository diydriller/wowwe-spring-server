package com.example.demo.src.video;

import com.example.demo.src.user.model.User;
import com.example.demo.src.video.dto.SearchVideoResDto;
import com.example.demo.src.video.dto.TimeVideoResDto;
import com.example.demo.src.video.dto.VideoResDto;
import com.example.demo.src.video.dto.VideoUploadReqDto;
import com.example.demo.src.video.model.Video;
import com.example.demo.src.videoLike.dto.TopLikeVideoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoService {

    @Autowired
    VideoRepository videoRepository;


    public List<Video> findVideo(){
        return videoRepository.findAll();
    }


    @Transactional
    public void save(Video video) {
        videoRepository.save(video);
    }


    @Transactional
    public void delete(Video video) {
        videoRepository.delete(video);
    }



    @Transactional
    public Page<SearchVideoResDto> searchVideoPaging(int offset, int limit, String title) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Video> page = videoRepository.findByTitle(title, pageRequest);
        Page<SearchVideoResDto> apiDto = page.map(v -> new SearchVideoResDto(v.getId(), v.getTitle(), v.getCreatedTime()));

        return apiDto;
    }


    @Transactional
    public Page<TimeVideoResDto> recentlyVideoPaging(int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.Direction.DESC, "createdTime");

        Page<Video> page = videoRepository.findAll(pageRequest);

        Page<TimeVideoResDto> apiDto = page.map(v -> new TimeVideoResDto(v.getId(), v.getThumnailImg(), v.getTitle(), v.getCreatedTime(), v.getUser().getId(), v.getUser().getName(), v.getUser().getProfileImg()));

        return apiDto;
    }


    public Video findById(Long videoId) {
        Optional<Video> video = videoRepository.findById(videoId);
        return video.get();
    }



    @Transactional
    public void createVideo(VideoUploadReqDto videoUploadReqDto, double duration, String videoDownUrl, String imgDownUrl, User user){


        Video video=new Video();

        video.setUser(user);
        video.setTitle(videoUploadReqDto.getTitle());
        video.setDescription(videoUploadReqDto.getDescription());
        video.setDuration(duration);
        video.setThumnailImg(imgDownUrl);
        video.setFileUrl(videoDownUrl);

        videoRepository.save(video);
    }



}
