package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Insider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class InsiderResDto extends FeedResponseDto {

    private Long groupId;


    public InsiderResDto(Feed feed, int commentCnt, int likeCnt, boolean isClap, int category, Long groupId) {
        super(feed, commentCnt, likeCnt, isClap, category);
        this.groupId = groupId;
    }

}
