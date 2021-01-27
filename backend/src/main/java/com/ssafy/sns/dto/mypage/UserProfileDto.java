package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserProfileDto {

    private String username;
    private String user_id; // email
    private int fromMeToOthersCnt;
    private int toMeFromOthersCnt;
    private int groupCnt;
    private String introduce;

    private List<Indoor> newsfeeds; // Indoor => newsfeed 로 수정예정, 10개

}