package com.ssafy.sns.domain.hashtag;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.Builder;
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

    @Column(name = "hashtag_name", nullable = false, unique = true)
    private String tagName;

    @OneToMany(mappedBy = "hashtag")
    private List<IndoorHashtag> indoorHashtags = new ArrayList<>();

    @Builder
    public Hashtag(String tagName) {
        this.tagName = tagName;
    }

    public void addIndoorHashtag(IndoorHashtag indoorHashtag) {
        if (indoorHashtags == null) indoorHashtags = new ArrayList<>();
        this.indoorHashtags.add(indoorHashtag);
        indoorHashtag.setHashtag(this);
    }
}
