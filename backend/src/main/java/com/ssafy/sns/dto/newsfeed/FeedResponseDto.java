package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.file.File;
import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class FeedResponseDto {

    private Long id;
    private SimpleUserDto user;
    private String content;
    private List<String> files = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private LocalDateTime date;
    private int commentCnt;
    private int likeCnt;
    private int category;
    private boolean isClap;
    private boolean isFollow;

    public FeedResponseDto(Feed feed, int commentCnt, int likeCnt,boolean isClap, boolean isFollow) {
        this.id = feed.getId();
        this.user = new SimpleUserDto(feed.getUser());
        this.content = feed.getContent();
        this.date = feed.getCreatedDate();
        if (tags == null) tags = new ArrayList<>();
        for (FeedHashtag feedHashtag : feed.getFeedHashtagList()) {
            tags.add(feedHashtag.getHashtag().getTagName());
        }
        for (File file : feed.getFileList()) {
            files.add(file.getFileName());
        }
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
        this.isClap = isClap;
        this.isFollow = isFollow;
    }

    @Builder
    public FeedResponseDto(Feed feed, int commentCnt, int likeCnt, boolean isClap, int category, boolean isFollow) {
        this.id = feed.getId();
        this.user = new SimpleUserDto(feed.getUser());
        this.content = feed.getContent();
        this.date = feed.getCreatedDate();
        if (tags == null) tags = new ArrayList<>();
        for (FeedHashtag feedHashtag : feed.getFeedHashtagList()) {
            tags.add(feedHashtag.getHashtag().getTagName());
        }
        for (File file : feed.getFileList()) {
            files.add(file.getFileName());
        }
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
        this.isClap = isClap;
        this.category = category;
        this.isFollow = isFollow;
    }
}
