package com.ssafy.sns.service;

import com.ssafy.sns.dto.FeedDto;
import com.ssafy.sns.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;

    @Autowired
    public FeedServiceImpl(FeedRepository feedRepository) { this.feedRepository = feedRepository; }

    @Override
    public List<FeedDto> showList(Long cnt) {
        return null;
    }

    @Override
    public FeedDto read(Long id) {
        return null;
    }

    @Override
    public boolean write(FeedDto feed) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean modify(FeedDto feed) {
        return false;
    }
}
