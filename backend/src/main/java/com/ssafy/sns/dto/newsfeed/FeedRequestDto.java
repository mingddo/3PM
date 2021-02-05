package com.ssafy.sns.dto.newsfeed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDto {

    private String content;
    private List<String> filePaths;
    private List<String> tags;
}
