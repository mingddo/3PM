package com.ssafy.sns.domain.follow;

import com.ssafy.sns.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @Column(name = "from")
    private Long fromId; // 내가 구독하는 사람들

    @Column(name = "to")
    private Long toId; // 나를 구독하는 사람들
}
