package com.ssafy.sns.dto.newsfeed;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class IndoorRequestDto extends FeedRequestDto {

    @Builder
    public IndoorRequestDto(String content, String file, List<String> tags) {
        super(content, null, tags);
    }
}
