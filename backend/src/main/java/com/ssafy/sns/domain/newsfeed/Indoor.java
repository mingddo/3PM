package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Indoor extends Feed {

    // 기능 추가
    private String test;

    @Builder
    public Indoor(String content, User user, List<FeedHashtag> feedHashtags, String test) {
        super(content, user, feedHashtags);
        this.test = test;
    }

    public void update(IndoorRequestDto indoorRequestDto) {
        super.update(indoorRequestDto.getContent());
        this.test = indoorRequestDto.getTest();
    }
}
