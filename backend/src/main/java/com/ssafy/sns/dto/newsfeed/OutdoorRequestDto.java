package com.ssafy.sns.dto.newsfeed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class OutdoorRequestDto extends FeedRequestDto {

    Long lat;
    Long lng;
    String address;
    String placeName;

    @Builder
    public OutdoorRequestDto(String content, String file, List<String> tags) {
        super(content, null, tags);
    }
}
