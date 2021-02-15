package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Indoor extends Feed {

    public Indoor(String content, User user) {
        super(content, user, null);
    }

    public void update(IndoorRequestDto indoorRequestDto) {
        super.update(indoorRequestDto.getContent());
    }
}
