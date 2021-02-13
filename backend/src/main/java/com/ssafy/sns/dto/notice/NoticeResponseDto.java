package com.ssafy.sns.dto.notice;

import com.ssafy.sns.domain.user.User;
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
    private Long other;
    private Long me;


    public NoticeResponseDto(int type, Long other, Long me) {
        this.type = type;
        this.other = other;
        this.me = me;
    }

//    @Builder
//    public NoticeResponseDto(int type, User me) {
//        this.type = type;
//        this.me = me;
//    }
}
