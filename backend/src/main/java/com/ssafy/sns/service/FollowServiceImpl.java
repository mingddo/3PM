package com.ssafy.sns.service;

import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl {

    private final FollowRepository followRepository;

    public int fromMeToOthers(Long id) {
        return (int) followRepository.countByFromId(id);
    }

    public int toMeFromOthers(Long id) {
        return (int) followRepository.countByToId(id);
    }

    public List<Long> fromMeToOthersList(Long id) {
        List<Follow> follows = followRepository.findByFromId(id);

        List<Long> result = new ArrayList<>();
        follows.stream().forEach(follow -> result.add(follow.getFromId()));
        return result;
    }

    public List<Long> toMeFromOthersList(Long id) {
        List<Follow> follows = followRepository.findByToId(id);

        List<Long> result = new ArrayList<>();
        follows.stream().forEach(follow -> result.add(follow.getToId()));
        return result;
    }
}
