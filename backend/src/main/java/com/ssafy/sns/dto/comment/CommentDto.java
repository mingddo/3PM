package com.ssafy.sns.dto.comment;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDto {

    private String content;
    private SimpleUserDto user;
    private LocalDateTime date;
    private int clapCnt;
    private boolean isClap;

    @Builder
    public CommentDto(String content, SimpleUserDto user, LocalDateTime date, int clapCnt, boolean isClap) {
        this.content = content;
        this.user = user;
        this.date = date;
        this.clapCnt = clapCnt;
        this.isClap =isClap;
    }

    public CommentDto(Comment comment, int clapCnt, boolean isClap) {
        this.content = comment.getContent();
        this.user = new SimpleUserDto(comment.getUser());
        this.date = comment.getCreatedDate();
        this.clapCnt = clapCnt;
        this.isClap = isClap;
    }
}
