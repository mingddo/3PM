package com.ssafy.sns.dto.comment;

import com.ssafy.sns.dto.user.SimpleUserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private String content;
    private SimpleUserDto user;
    private LocalDateTime date;
}
