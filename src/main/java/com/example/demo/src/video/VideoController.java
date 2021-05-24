package com.example.demo.src.video;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.User;
import com.example.demo.src.video.dto.VideoUploadReqDto;
import com.example.demo.src.video.model.Video;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.INVALID_JWT;
import static com.example.demo.config.BaseResponseStatus.SUCCESS;

@Controller
@RequiredArgsConstructor
public class VideoController {


    @Value("${file.upload.path}")
    private String fileUploadPath;
    @Value("${file.down.baseUrl}")
    private String fileDownBaseUrl;
    @Value("${file.ffmpeg.path}")
    private String ffmpegPath;
    @Value("${file.ffprobe.path}")
    private String ffprobePath;

    private final VideoService videoService;
    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping("recently")
    public String recentlyVideo(Model model) {

        videoService.save(new Video("chang", "first", 10));

        List<Video> videoList = videoService.findVideo();
        model.addAttribute("videos", videoList);
        return "videos/videoHome";
    }


    //비디오 업로드
    @ResponseBody
    @PostMapping("/video/upload")
    public BaseResponse upload(@RequestParam("file") MultipartFile file,
                               VideoUploadReqDto videoUploadReqDto) throws BaseException{
        try{


            Long userId=jwtService.getUserId();
            Optional<User> opt=userService.findByUserId(userId);
            opt.orElseThrow(()->new BaseException(INVALID_JWT));
            User user=opt.get();


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String current_date = simpleDateFormat.format(new Date());
            String basePath = new File("").getAbsolutePath() + fileUploadPath;
            String[] fileFlags = file.getOriginalFilename().split("\\.");
            String videoPath = basePath +"video"+current_date + "." + fileFlags[fileFlags.length-1];
            String videoDownUrl=fileDownBaseUrl+"video"+current_date + "." + fileFlags[fileFlags.length-1];

            File dest = new File(videoPath);
            file.transferTo(dest);

            FFmpeg ffmpeg=new FFmpeg(ffmpegPath);
            FFprobe ffprobe=new FFprobe(ffprobePath);

            String imgPath=basePath+"img"+current_date+".png";
            String imgDownUrl=fileDownBaseUrl+"img"+current_date+".png";

            FFmpegBuilder builder = new FFmpegBuilder()
                    .overrideOutputFiles(true)
                    .setInput(videoPath)
                    .addExtraArgs("-ss","00:00:01")
                    .addOutput(imgPath)
                    .setFrames(1)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);
            executor.createJob(builder).run();

            FFmpegProbeResult probeResult=ffprobe.probe(videoPath);
            FFmpegFormat format = probeResult.getFormat();

            videoService.createVideo(videoUploadReqDto,format.duration,videoDownUrl,imgDownUrl,user);


            return new BaseResponse(SUCCESS);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return new BaseResponse(SUCCESS);
    }

}
