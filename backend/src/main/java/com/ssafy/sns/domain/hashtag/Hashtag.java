package com.ssafy.sns.domain.hashtag;

import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======
import lombok.Setter;
>>>>>>> feature/mypage_BE

import javax.persistence.*;

@Getter
<<<<<<< HEAD
=======
@Setter
>>>>>>> feature/mypage_BE
@NoArgsConstructor
@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_name")
    private String tagName;
}
