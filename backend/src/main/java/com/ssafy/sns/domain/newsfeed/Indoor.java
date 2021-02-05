package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.hashtag.FeedHashtag;
import com.ssafy.sns.domain.user.User;
import lombok.*;

import javax.persistence.*;
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

    public void update(String content, String test) {
        super.update(content);
        this.test = test;

    }
}
