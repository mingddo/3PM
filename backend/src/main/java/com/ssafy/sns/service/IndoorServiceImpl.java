package com.ssafy.sns.service;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.IndoorListResponseDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.repository.HashtagRepository;
import com.ssafy.sns.repository.HashtagRepositoryImpl;
import com.ssafy.sns.repository.IndoorRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IndoorServiceImpl implements FeedService {

    private final IndoorRepositoryImpl indoorRepository;
    private final HashtagRepositoryImpl hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public IndoorListResponseDto readMyList(Long id, int num) {
        List<Indoor> indoorList = indoorRepository.findMyList(id, num);
        return new IndoorListResponseDto(indoorList, num + indoorList.size());
    }

    @Override
    public IndoorListResponseDto readList(int num) {
        List<Indoor> indoorList = indoorRepository.findList(num);
        return new IndoorListResponseDto(indoorList, num + indoorList.size());
    }

    @Override
    public IndoorResponseDto read(Long id) {
        Indoor indoor = indoorRepository.findOne(id);
        return new IndoorResponseDto(indoor);
    }

    @Override
    public Long write(IndoorRequestDto indoorRequestDto) {
        User user = userRepository.findById(indoorRequestDto.getUserId()).orElse(null);
        List<Hashtag> hashtags = hashtagRepository.make(indoorRequestDto.getTags());
        return indoorRepository.save(indoorRequestDto.toEntity(user), hashtags);
    }

    @Override
    public void delete(Long id) {
        indoorRepository.remove(id);
    }

    @Override
    public Long modify(Long indoorId, IndoorRequestDto indoorRequestDto) {
        List<Hashtag> hashtags = hashtagRepository.make(indoorRequestDto.getTags());
        return indoorRepository.update(indoorId, indoorRequestDto);
    }
}
