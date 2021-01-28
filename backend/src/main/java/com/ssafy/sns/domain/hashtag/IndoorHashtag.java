package com.ssafy.sns.domain.hashtag;

import com.ssafy.sns.domain.newsfeed.Indoor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
<<<<<<< HEAD
=======
@Setter
>>>>>>> feature/mypage_BE
@NoArgsConstructor
@Entity
public class IndoorHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indoor_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indoor_id")
<<<<<<< HEAD
    @Setter
=======
>>>>>>> feature/mypage_BE
    private Indoor indoor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}
