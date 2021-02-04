package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.clap.IndoorClap;
import com.ssafy.sns.domain.file.File;
import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "CATEGORY")
public class Feed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @Lob
    @Column(name = "feed_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // casecade : 상태 전이
    // orphanRemoval : 고아 객체 삭제
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedHashtag> feedHashtagList = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndoorClap> indoorClapList = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> fileList = new ArrayList<>();

    public Feed(String content, User user, List<FeedHashtag> feedHashtagList) {
        this.content = content;
        this.user = user;
        this.feedHashtagList = feedHashtagList;
    }

    public void update(String content) {
        this.content = content;
        // 실제 파일 삭제 추가
//        this.file = file;
    }

    public void addFeedHashtag(FeedHashtag feedHashtag) {
        if (feedHashtagList == null) feedHashtagList = new ArrayList<>();
        feedHashtagList.add(feedHashtag);
        feedHashtag.setFeed(this);
    }

    public void deleteFeedHashtag(FeedHashtag feedHashtag) {
        feedHashtagList.remove(feedHashtag);
        feedHashtag.setFeed(null);
    }

    public void addIndoorClap(IndoorClap indoorClap) {
        if (indoorClapList == null) indoorClapList = new ArrayList<>();
        indoorClapList.add(indoorClap);
        indoorClap.setFeed(this);
    }

    public void deleteIndoorClap(IndoorClap indoorClap) {
        feedHashtagList.remove(indoorClap);
        indoorClap.setFeed(null);
    }

    public void addFile(File file) {
        if (fileList == null) fileList = new ArrayList<>();
        fileList.add(file);
        file.setFeed(this);
    }

    public void deleteFile(File file) {
        fileList.remove(file);
        file.setFeed(null);
    }

}
