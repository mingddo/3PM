package com.ssafy.sns.dto.notice;

import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor
public class NoticeResponseDto {

    private int type;
    private SimpleUserDto other;
    private Long feedId;
    private String category;

    @Builder
    public NoticeResponseDto(int type, SimpleUserDto other) {
        this.type = type;
        this.other = other;
    }

    @Builder
    public NoticeResponseDto(int type, SimpleUserDto other, Long feedId, String category) {
        this.type = type;
        this.other = other;
        this.feedId = feedId;
        this.category = category;
    }
}
