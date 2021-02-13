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

    @Builder
    public CommentDto(String content, SimpleUserDto user, LocalDateTime date) {
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public CommentDto(Comment comment) {
        this.content = comment.getContent();
        this.user = new SimpleUserDto(comment.getUser());
        this.date = comment.getCreatedDate();
    }
}
