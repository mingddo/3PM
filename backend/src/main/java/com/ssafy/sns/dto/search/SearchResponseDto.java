package com.ssafy.sns.dto.search;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.dto.newsfeed.IndoorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchResponseDto {

    private String tagName;
    private List<IndoorDto> indoors;

    public SearchResponseDto(String tagName, List<IndoorDto> indoors) {
        this.tagName = tagName;
        this.indoors = indoors;
    }
}
