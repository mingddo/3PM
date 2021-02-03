package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.AllArgsConstructor;
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
    private String file;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime date;

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.user = new SimpleUserDto(feed.getUser());
        this.content = feed.getContent();
        this.file = null;
        this.date = feed.getCreatedDate();
        if (tags == null) tags = new ArrayList<>();
        for (FeedHashtag feedHashtag : feed.getFeedHashtags()) {
            tags.add(feedHashtag.getHashtag().getTagName());
        }
    }
}
