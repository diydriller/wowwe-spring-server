package com.example.demo.src.videoComment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.User;
import com.example.demo.src.video.VideoService;
import com.example.demo.src.video.dto.UserDto;
import com.example.demo.src.video.model.Video;
import com.example.demo.src.videoComment.dto.*;
import com.example.demo.src.videoComment.model.VideoComment;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.LOGIN_USER_NOT_EQUAL_HOST;
import static com.example.demo.config.BaseResponseStatus.LOGIN_USER_NOT_EXISTS_EMAIL;

@RestController
@RequiredArgsConstructor
public class VideoCommentApi {

    @Autowired
    VideoCommentService videoCommentService;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/video/{videoId}/addComment")
    public BaseResponse<VideoCommentResDto> registerComment(@PathVariable("videoId") Long videoId, @RequestBody VideoCommentReqDto videoCommentDto) throws BaseException {

        Long userId = jwtService.getUserId();

        String comment = videoCommentDto.getComment();

        User user1 = userService.findById(userId);

        Video video1 = videoService.findById(videoId);

        VideoComment temp = videoCommentService.save(new VideoComment(user1, video1, comment));
        VideoCommentDto temp2 = new VideoCommentDto(temp.getId(), temp.getComment(), temp.getCreatedTime());
        UserDto userDto = new UserDto(userId, user1.getName(), user1.getProfileImg());

        VideoCommentResDto videoCommentResDto = new VideoCommentResDto(videoId, temp2, userDto);

        return new BaseResponse<>(videoCommentResDto);
    }


    @PostMapping("/video/editComment")
    public BaseResponse<VideoCommentUpdateResDto> updateComment(@RequestBody VideoCommentUpdateReqDto videoCommentUpdateReqDto) throws BaseException {

        Long userId = jwtService.getUserId();

        Long commentId = videoCommentUpdateReqDto.getCommentId();
        VideoComment videoComment = videoCommentService.findById(commentId);
        if (videoComment.getUser().getId().equals(userId)) {
            throw new BaseException(LOGIN_USER_NOT_EQUAL_HOST);
        }

        videoCommentService.update(videoComment, videoCommentUpdateReqDto.getCommentText());

        videoComment.setComment(videoCommentUpdateReqDto.getCommentText());

        VideoCommentUpdateResDto videoCommentUpdateResDto = new VideoCommentUpdateResDto(videoComment.getVideo().getId(), videoComment.getId(), videoComment.getComment());


        return new BaseResponse<>(videoCommentUpdateResDto);

    }

    @DeleteMapping("/video/removeComment")
    private BaseResponse<VideoCommentDeleteResDto> deleteComment(@RequestBody VideoCommentDeleteReqDto videoCommentDeleteReqDto) throws BaseException {

        Long userId = jwtService.getUserId();

        Long commentId = videoCommentDeleteReqDto.getCommentId();
        VideoComment videoComment = videoCommentService.findById(commentId);
        if (videoComment.getUser().getId().equals(userId)) {
            throw new BaseException(LOGIN_USER_NOT_EQUAL_HOST);
        }

        videoCommentService.delete(commentId);
        VideoCommentDeleteResDto videoCommentDeleteResDto = new VideoCommentDeleteResDto(videoComment.getVideo().getId(), commentId);
        return new BaseResponse<>(videoCommentDeleteResDto);
    }
}
