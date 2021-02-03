package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.hashtag.IndoorHashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IndoorDto {

    private Long id;
    private String content;
    private String file;
    private User user;
    private List<IndoorHashtag> indoorHashtags;

    public IndoorDto(Indoor indoor) {
        this.id = indoor.getId();
        this.content = indoor.getContent();
        this.file = indoor.getFile();
        this.user = indoor.getUser();
        this.indoorHashtags = indoor.getIndoorHashtags();
    }
}
