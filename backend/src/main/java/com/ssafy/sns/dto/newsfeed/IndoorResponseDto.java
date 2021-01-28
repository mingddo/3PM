package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.dto.SimpleUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class IndoorResponseDto {

    private SimpleUserDto user;
    private String content;
    private String file;
    private List<String> tags;
    private Long commentCnt;
    private Long likeCnt;
    private LocalDateTime localDateTime;

    @Builder
    public IndoorResponseDto(SimpleUserDto user, String content, String file, List<String> tags, Long commentCnt, Long likeCnt, LocalDateTime localDateTime) {
        this.user = user;
        this.content = content;
        this.file = file;
        this.tags = tags;
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
        this.localDateTime = localDateTime;
    }
}
