package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class IndoorRequestDto extends FeedRequestDto {

    private String test;

    @Builder
    public IndoorRequestDto(String content, String file, List<String> tags, Long userId, String test) {
        super(content, null, tags, userId);
        this.test = test;
    }
}
