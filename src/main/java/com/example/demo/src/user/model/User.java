package com.example.demo.src.user.model;

import com.example.demo.config.BaseTimeEntity;
import com.example.demo.src.videoComment.model.VideoComment;
import com.example.demo.src.videoLike.model.VideoLike;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private String profileImg;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VideoLike> videoLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<VideoComment> videoComments = new ArrayList<>();



    public User() {

    }
    public User(String userName) {
        this.name = userName;
    }

    public User(String name, String email, List<VideoLike> videoLikes) {
        this.name = name;
        this.email = email;
        this.videoLikes = videoLikes;
    }

    public void videoAddLike(VideoLike videoLike) {
        this.videoLikes.add(videoLike);
        videoLike.setUser(this);
    }

    public void videoAddComment(VideoComment videoComment) {
        this.videoComments.add(videoComment);
        videoComment.setUser(this);
    }

    public void updateUser(UpdateUserReq user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.profileImg = user.getProfileImg();
    }

}
