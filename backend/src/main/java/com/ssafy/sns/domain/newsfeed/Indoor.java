package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.hashtag.IndoorHashtag;
import com.ssafy.sns.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Indoor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_id")
    private Long id;

    @Lob
    @Column(name = "indoor_content")
    private String content;

    @Lob
    @Column(name = "indoor_file")
    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "indoor")
    private List<IndoorHashtag> indoorHashtags = new ArrayList<>();

    @Builder
    public Indoor(String content, String file, User user) {
        this.content = content;
        this.file = file;
        this.user = user;
    }

    public void addIndoorHashtag(IndoorHashtag indoorHashtag) {
        indoorHashtags.add(indoorHashtag);
        indoorHashtag.setIndoor(this);
    }

    public void update(String content, String file, List<String> tags) {
        this.content = content;
        this.file = file;
        // 태그추가하기
    }
}
