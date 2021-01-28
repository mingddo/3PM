package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.user.SimpleUserDto;
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

    private Long indoorId;
    private SimpleUserDto user;
    private String content;
    private String file;
    private List<String> tags;
    private Long commentCnt;
    private Long likeCnt;
    private LocalDateTime localDateTime;

    @Builder
    public IndoorResponseDto(Indoor indoor) {
        this.indoorId = indoor.getId();
        this.user = SimpleUserDto.builder()
                .id(indoor.getUser().getId())
                .nickname(indoor.getUser().getNickname())
                .img(indoor.getUser().getImg())
                .build();
        this.content = indoor.getContent();
        this.file = indoor.getFile();
        this.tags = null;
        this.commentCnt = null;
        this.likeCnt = null;
        this.localDateTime = indoor.getCreatedDate();
    }
}
