package com.ssafy.sns.domain.hashtag;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name", unique = true)
    private String tagName;

    @OneToMany(mappedBy = "hashtag")
    private List<IndoorHashtag> indoorHashtags = new ArrayList<>();

    public void addIndoorHashtag(IndoorHashtag indoorHashtag) {
        this.indoorHashtags.add(indoorHashtag);
        indoorHashtag.setHashtag(this);
    }
}
