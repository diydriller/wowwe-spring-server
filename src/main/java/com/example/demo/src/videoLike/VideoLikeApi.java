package com.example.demo.src.videoLike;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.User;
import com.example.demo.src.videoLike.dto.AddLikeDto;
import com.example.demo.src.videoLike.dto.AddLikeResDto;
import com.example.demo.src.videoLike.dto.TopLikeVideoResDto;
import com.example.demo.src.videoLike.dto.temp;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.INVALID_JWT;
import static com.example.demo.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
public class VideoLikeApi {

    @Autowired
    VideoLikeService videoLikeService;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;

    @GetMapping("/loadTop100Video")
    public BaseResponse<Page<TopLikeVideoResDto>> paging(@RequestParam int offset, @RequestParam int limit) {
        Page<TopLikeVideoResDto> topLikeVideoPaging = videoLikeService.topLikeVideoPaging(offset, limit);

        return new BaseResponse<>(topLikeVideoPaging);
    }

    @PostMapping("/video/{videoId}/addLike")
    public BaseResponse addlike(@PathVariable("videoId") Long videoId) throws BaseException {

        Long userId = jwtService.getUserId();
        videoLikeService.videoLike(userId, videoId);

        return new BaseResponse(SUCCESS);

    }

    @DeleteMapping("/video/{videoId}/removeLike")
    public BaseResponse removeLike(@PathVariable("videoId") Long videoId) throws BaseException {

        Long userId = jwtService.getUserId();
        Optional<User> opt = userService.findByUserId(userId);
        opt.orElseThrow(() -> new BaseException(INVALID_JWT));
        User user = opt.get();

        videoLikeService.videoUnlike(userId, videoId);

        return new BaseResponse(SUCCESS);
    }
}
