package com.ssafy.sns.dto.newsfeed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class  FeedListResponseDto<T> {

    private List<T> feedList;
    private int endNum;

    @Builder
    public FeedListResponseDto(List<T> feedList, int endNum) {
        this.feedList = feedList;
        this.endNum = endNum;
    }
}
