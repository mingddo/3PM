package com.ssafy.sns.domain.newsfeed;

import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import com.ssafy.sns.dto.newsfeed.WorkerRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor
public class Worker extends Feed {

    public Worker(FeedRequestDto feedRequestDto, User user) {
        super(feedRequestDto.getContent(), user, null);
    }

    public void update(WorkerRequestDto workerRequestDto) {
        super.update(workerRequestDto.getContent());
    }
}
