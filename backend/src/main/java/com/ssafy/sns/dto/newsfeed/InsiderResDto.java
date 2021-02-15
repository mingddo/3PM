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
    private String groupName;


    public InsiderResDto(Feed feed, int commentCnt, int likeCnt, boolean isClap, int category, boolean isFollow, Long groupId, String groupName) {
        super(feed, commentCnt, likeCnt, isClap, category, isFollow);
        this.groupId = groupId;
        this.groupName = groupName;
    }

}
