package com.ssafy.sns.service;

import com.ssafy.sns.dto.comment.CommentRequestDto;
import com.ssafy.sns.repository.CommentRepository;
import com.ssafy.sns.repository.CommentRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryImpl commentRepository;
    private final UserRepository userRepository;

    @Override
    public Long write(CommentRequestDto commentRequestDto, Long userId, Long feedId) {
        return null;
    }
}
