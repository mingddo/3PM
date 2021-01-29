package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class IndoorListResponseDto {

    private List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
    private int endNum;

    @Builder
    public IndoorListResponseDto(List<Indoor> indoorList, int endNum) {
        for (Indoor i : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto(i));
        }
        this.endNum = endNum;
    }
}
