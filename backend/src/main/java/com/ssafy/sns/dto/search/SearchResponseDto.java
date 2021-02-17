package com.ssafy.sns.dto.search;

import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.newsfeed.FeedResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.dto.newsfeed.InsiderResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class SearchResponseDto {
    private String tagName;
    private List<InsiderResDto> indoorResponseDtoList = new ArrayList<>();

    @Builder
    public SearchResponseDto(String tagName, List<InsiderResDto> indoorResponseDtoList) {
        this.tagName = tagName;
        this.indoorResponseDtoList = indoorResponseDtoList;
    }

}
