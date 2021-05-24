package com.example.demo.src.videoLike;


import com.example.demo.src.video.model.Video;
import com.example.demo.src.videoLike.model.VideoLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {


    // count(vl) 은 해야하는가
    @Query("select vl from VideoLike vl group by vl.video order by vl.video.videoLikes.size desc")
    public Page<VideoLike> findVideoByLike(Pageable pageable);


    @Query("select count(vl) from VideoLike vl where vl.video.id = :id")
    public int findVideoLikesById(@Param("id") Long id);

    @Query("select vl from VideoLike vl where vl.video.id = :videoId and vl.user.id = :userId")
    public VideoLike findByuserIdAndvideoId(@Param("videoId") Long videoId, @Param("userId") Long userId);
}
