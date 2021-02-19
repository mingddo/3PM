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

    Double lat;
    Double lng;
    String address;
    String placeName;
    Integer code;
}
