package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.repository.FeedClapRepositoryImpl;
import com.ssafy.sns.repository.IndoorRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedClapServiceImpl implements ClapService {

    private final FeedClapRepositoryImpl feedClapRepository;
    private final UserRepository userRepository;
    private final IndoorRepositoryImpl indoorRepository;

    @Override
    public void changeClap(Long userId, Long feedId) {
        User user = userRepository.findById(userId).orElse(null);
        Feed feed = indoorRepository.findOne(feedId);

        List<FeedClap> clapList = feedClapRepository.findClap(user, feed);
        if (!clapList.isEmpty()) {
            FeedClap feedClap = clapList.get(0);
            user.deleteFeedClap(feedClap);
            feed.deleteFeedClap(feedClap);
            feedClapRepository.delete(feedClap);
        } else {
            FeedClap feedClap = feedClapRepository.save(new FeedClap());
            user.addFeedClap(feedClap);
            feed.addFeedClap(feedClap);
        }
    }
}
