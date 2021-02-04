package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.*;
import com.ssafy.sns.repository.HashtagRepositoryImpl;
import com.ssafy.sns.repository.IndoorRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IndoorServiceImpl implements FeedService {

    private final IndoorRepositoryImpl indoorRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public FeedListResponseDto readMyList(Long id, int num) {
        List<Feed> indoorList = indoorRepository.findMyList(id, num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedListResponseDto readList(int num) {
        List<Feed> indoorList = indoorRepository.findList(num);
        List<IndoorResponseDto> indoorResponseDtoList = new ArrayList<>();
        for (Feed feed : indoorList) {
            indoorResponseDtoList.add(new IndoorResponseDto((Indoor) feed));
        }
        return new FeedListResponseDto<>(indoorResponseDtoList, num + indoorList.size());
    }

    @Override
    public FeedResponseDto read(Long id) {
        Indoor indoor = (Indoor) indoorRepository.findOne(id);
        return new IndoorResponseDto(indoor);
    }

    @Override
    public Long write(FeedRequestDto feedRequestDto) {
        // 유저 정보
        User user = userRepository.findById(feedRequestDto.getUserId()).orElse(null);
        // 글 등록
        Indoor indoor = (Indoor) indoorRepository.save(feedRequestDto, user);
        // 태그 등록
        hashtagRepository.make(feedRequestDto.getTags(), indoor);

        return indoor.getId();
    }

    @Override
    public void delete(Long id) {
        indoorRepository.remove(id);
    }

    @Override
    public Long modify(Long id, FeedRequestDto feedRequestDto) {
        // 글 찾기
        Indoor indoor = (Indoor) indoorRepository.update(id, feedRequestDto);
        // 태그 찾고 삭제
        hashtagRepository.change(feedRequestDto.getTags(), indoor);

        return indoor.getId();
    }

    @Override
    public Long addClap(Long uid, Long fid) {
//        // 글 찾기
//        Indoor indoor = (Indoor) indoorRepository.findOne(fid);
        return null;

    }
}
