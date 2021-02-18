package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Outdoor;

public class OutdoorResDto extends FeedResponseDto{

    Double lat;
    Double lng;
    String address;
    String placeName;
    Integer code;

    public OutdoorResDto(Outdoor outdoor, int commentCnt, int likeCnt, boolean isClap, boolean isFollow) {
        super(outdoor, commentCnt, likeCnt, isClap, 3, isFollow);
        this.lat = outdoor.getLat();
        this.lng = outdoor.getLng();
        this.address = outdoor.getAddress();
        this.placeName = outdoor.getPlaceName();
        this.code = outdoor.getCode();
    }
}
