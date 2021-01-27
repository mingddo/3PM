package com.ssafy.sns.service;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.repository.IndoorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IndoorService {

    private final IndoorRepository indoorRepository;

    public List<Indoor> findAll() {
        return indoorRepository.findAll();
    }
}
