package com.ssafy.sns.dto.mypage;

import com.ssafy.sns.dto.indoor.IndoorDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class RecentActivityDto {

    private List<String> alertImgAddrList;
    private List<IndoorDto> newsfeeds;
    // private List<
}
