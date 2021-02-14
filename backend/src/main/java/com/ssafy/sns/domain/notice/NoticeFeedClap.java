package com.ssafy.sns.domain.notice;

import com.ssafy.sns.domain.clap.FeedClap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class NoticeFeedClap extends Notice{

    @Column(name = "feed_clab_id")
    private Long feed_clab_id;

    @Builder
    public NoticeFeedClap(Long feed_clab_id) {
        this.feed_clab_id = feed_clab_id;
    }

    public void deleteFeedClap(FeedClap clap) {
        clap.setFeed(null);
    }
}
