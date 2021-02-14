package com.ssafy.sns.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SearchHashtagDto {

    String tagName;
    int cnt;

    @Builder
    public SearchHashtagDto(String tagName, int cnt) {
        this.tagName = tagName;
        this.cnt = cnt;
    }
}
