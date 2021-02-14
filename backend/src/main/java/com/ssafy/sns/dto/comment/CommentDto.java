package com.ssafy.sns.dto.comment;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private SimpleUserDto user;
    private LocalDateTime date;
    private int clapCnt;
    private boolean isClap;

    public CommentDto(Comment comment, int clapCnt, boolean isClap) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = new SimpleUserDto(comment.getUser());
        this.date = comment.getCreatedDate();
        this.clapCnt = clapCnt;
        this.isClap = isClap;
    }
}
