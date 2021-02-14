package com.ssafy.sns.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponseDto {

    private List<CommentDto> comments;
    private int endNum;

    @Builder
    public CommentResponseDto(List<CommentDto> comments, int endNum) {
        this.comments = comments;
        this.endNum = endNum + comments.size();
    }
}
