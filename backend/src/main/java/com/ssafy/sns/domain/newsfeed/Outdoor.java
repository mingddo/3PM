package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import com.ssafy.sns.dto.newsfeed.OutdoorRequestDto;
import com.ssafy.sns.dto.newsfeed.WorkerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Outdoor extends Feed {

    Double lat;
    Double lng;
    String address;
    String placeName;
    Integer code;

    public Outdoor(FeedRequestDto feedRequestDto, User user) {
        super(feedRequestDto.getContent(), user,null);
        this.lat = ((OutdoorRequestDto)feedRequestDto).getLat();
        this.lng = ((OutdoorRequestDto)feedRequestDto).getLng();
        this.address = ((OutdoorRequestDto)feedRequestDto).getAddress();
        this.placeName = ((OutdoorRequestDto)feedRequestDto).getPlaceName();
        this.code = ((OutdoorRequestDto)feedRequestDto).getCode();
    }

    public void update(OutdoorRequestDto outdoorRequestDto) {
        super.update(outdoorRequestDto.getContent());
    }
}
