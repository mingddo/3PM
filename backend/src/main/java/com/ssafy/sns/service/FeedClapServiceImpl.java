package com.ssafy.sns.service;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.SimpleUserDto;
import com.ssafy.sns.repository.FeedFeedClapRepositoryImpl;
import com.ssafy.sns.repository.IndoorRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedClapServiceImpl implements ClapService {

    private final FeedFeedClapRepositoryImpl feedClapRepository;
    private final UserRepository userRepository;
    private final IndoorRepositoryImpl indoorRepository;

    @Override
    public void changeClap(Long userId, Long feedId) {
        User user = userRepository.findById(userId).orElse(null);
        Feed feed = indoorRepository.findById(feedId);

        Optional<FeedClap> resultClap = feedClapRepository.findClap(user, feed);
        if (resultClap.isPresent()) {
            user.deleteFeedClap(resultClap.get());
            feed.deleteFeedClap(resultClap.get());
            feedClapRepository.delete(resultClap.get());
        } else {
            FeedClap feedClap = feedClapRepository.save(new FeedClap());
            user.addFeedClap(feedClap);
            feed.addFeedClap(feedClap);
        }
    }

    @Override
    public List<SimpleUserDto> clapUserList(Long feedId) {
        Feed feed = indoorRepository.findById(feedId);
        if (feed == null) {
            // 없는 피드 요청
            return null;
        }
        List<Long> clapIds = feedClapRepository.findClapAll(feed)
                .stream()
                .map(feedClap -> feedClap.getUser().getId())
                .collect(Collectors.toList());

        return userRepository.findByIdIn(clapIds)
                .stream()
                .map(SimpleUserDto::new)
                .collect(Collectors.toList());

    }
}
