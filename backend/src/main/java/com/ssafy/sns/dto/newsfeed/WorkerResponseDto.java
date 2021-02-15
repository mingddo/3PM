package com.ssafy.sns.dto.newsfeed;

import com.ssafy.sns.domain.newsfeed.Worker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class WorkerResponseDto extends FeedResponseDto {

    public WorkerResponseDto(Worker worker, int commentCnt, int likeCnt, boolean isClap, boolean isFollow) {
        super(worker, commentCnt, likeCnt, isClap,4, isFollow);
    }
}
