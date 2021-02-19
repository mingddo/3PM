package com.ssafy.sns.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class SearchUserDto {

    private String value;
    private String label;

    @Builder
    public SearchUserDto(String tagName) {
        this.value = tagName;
        this.label = tagName;
    }
}
