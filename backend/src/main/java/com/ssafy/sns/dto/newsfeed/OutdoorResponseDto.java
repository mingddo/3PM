package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Outdoor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class OutdoorResponseDto extends FeedResponseDto {

    Double lat;
    Double lng;
    String address;
    String placeName;
    Integer code;

    public OutdoorResponseDto(Outdoor outdoor, int commentCnt, int likeCnt, boolean isClap, boolean isFollow) {
        super(outdoor, commentCnt, likeCnt, isClap,3, isFollow);
        this.lat = outdoor.getLat();
        this.lng = outdoor.getLng();
        this.address = outdoor.getAddress();
        this.placeName = outdoor.getPlaceName();
        this.code = outdoor.getCode();
    }
}
