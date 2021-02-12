package com.ssafy.sns.domain.comment;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.comment.CommentRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    @Setter
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Comment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
