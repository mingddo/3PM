package com.ssafy.sns.domain.notice;

import com.ssafy.sns.domain.clap.FeedClap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity
@NoArgsConstructor
public class NoticeFeedClap extends Notice{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_clab_id")
    private FeedClap feedClap;

    @Builder
    public NoticeFeedClap(FeedClap feedClap) {
        this.feedClap = feedClap;
    }
}
