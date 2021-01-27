package com.ssafy.sns.domain.hashtag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Hashtag {

    // 해시태그 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    // 해시태그 명
    @Column(name = "hashtag_name")
    private String tagName;
}
