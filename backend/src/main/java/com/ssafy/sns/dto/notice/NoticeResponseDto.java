package com.ssafy.sns.dto.notice;

import com.ssafy.sns.dto.group.SimpleGroupResDto;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class NoticeResponseDto {

    private int type;
    private SimpleUserDto other;
    private Long feedId;
    private String category;
    private String commentContent;
    private SimpleGroupResDto group;

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

    @Builder
    public NoticeResponseDto(int type, SimpleUserDto other, Long feedId, String category, String commentContent) {
        this.type = type;
        this.other = other;
        this.feedId = feedId;
        this.category = category;
        this.commentContent = commentContent;
    }

    @Builder
    public NoticeResponseDto(int type, SimpleUserDto other, SimpleGroupResDto group) {
        this.type = type;
        this.other = other;
        this.group = group;
    }
}
