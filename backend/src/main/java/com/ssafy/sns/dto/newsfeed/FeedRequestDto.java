package com.ssafy.sns.dto.newsfeed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDto {

    private String content;
    private String file;
    private List<String> tags;
    private Long userId;
}
