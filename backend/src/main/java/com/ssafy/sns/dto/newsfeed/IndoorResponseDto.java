package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
public class IndoorResponseDto extends FeedResponseDto {

    public IndoorResponseDto(Indoor indoor, int commentCnt, int likeCnt, boolean isClap, boolean isFollow) {
        super(indoor, commentCnt, likeCnt, isClap, 1, isFollow);
    }

//    @Builder
//    public IndoorResponseDto(Indoor indoor) {
//        this.indoorId = indoor.getId();
//        this.user = SimpleUserDto.builder()
//                .id(indoor.getUser().getId())
//                .nickname(indoor.getUser().getNickname())
//                .img(indoor.getUser().getImg())
//                .build();
//        this.content = indoor.getContent();
//        this.file = indoor.getFile();
//        this.tags = null;
//        this.commentCnt = null;
//        this.likeCnt = null;
//        this.localDateTime = indoor.getCreatedDate();
//    }
}
