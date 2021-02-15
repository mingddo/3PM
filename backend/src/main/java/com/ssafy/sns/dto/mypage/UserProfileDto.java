package com.ssafy.sns.dto.mypage;

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
    private Long user_id;
    private String user_img;
    private int fromMeToOthersCnt;
    private int toMeFromOthersCnt;
    private int groupCnt;
    private String introduce;
    private List<String> groups;

}