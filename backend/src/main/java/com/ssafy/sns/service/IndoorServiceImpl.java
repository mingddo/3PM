package com.ssafy.sns.service;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.SimpleUserDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorResponseDto;
import com.ssafy.sns.repository.FeedRepository;
import com.ssafy.sns.repository.IndoorRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndoorServiceImpl implements FeedService {

    private final IndoorRepositoryImpl indoorRepository;

    @Override
    public IndoorResponseDto read(Long id) {
        Indoor indoor = indoorRepository.findOne(id);

        return IndoorResponseDto.builder()
                .user(SimpleUserDto.builder()
                        .id(indoor.getUser().getId())
                        .nickname(indoor.getUser().getNickname())
                        .img(indoor.getUser().getImg())
                        .build())
                .content(indoor.getContent())
                .file(indoor.getFile())
                .tags(null)
                .commentCnt(null)
                .likeCnt(null)
                .build();
    }

    @Override
    @Transactional
    public Long write(IndoorRequestDto indoorRequestDto) {

        User user = indoorRepository.findUser(indoorRequestDto.getUserId());

        return indoorRepository.save(indoorRequestDto.toEntity(user));
    }

}
