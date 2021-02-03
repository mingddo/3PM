package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.BaseTimeEntity;
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

//    @Lob
//    @Column(name = "feed_file")
//    private List<String> file = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedHashtag> feedHashtags = new ArrayList<>();

    public Feed(String content, User user, List<FeedHashtag> feedHashtags) {
        this.content = content;
        this.user = user;
        this.feedHashtags = feedHashtags;
    }

    public void addFeedHashtag(FeedHashtag feedHashtag) {
        if (feedHashtags == null) feedHashtags = new ArrayList<>();
        feedHashtags.add(feedHashtag);
        feedHashtag.setFeed(this);
    }

    public void update(String content) {
        this.content = content;
        // 실제 파일 삭제 추가
//        this.file = file;
    }

    public void deleteFeedHashtag(FeedHashtag feedHashtag) {
        feedHashtags.remove(feedHashtag);
        feedHashtag.setFeed(null);
    }
}
