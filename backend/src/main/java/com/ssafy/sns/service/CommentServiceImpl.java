package com.ssafy.sns.service;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.comment.CommentRequestDto;
import com.ssafy.sns.repository.CommentRepositoryImpl;
import com.ssafy.sns.repository.FeedRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryImpl commentRepository;
    private final FeedRepositoryImpl feedRepository;
    private final UserRepository userRepository;

    @Override
    public void write(CommentRequestDto commentRequestDto, Long userId, Long feedId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Feed feed = feedRepository.findById(feedId);

        Comment comment = commentRepository.save(new Comment(commentRequestDto));
        user.addComment(comment);
        feed.addComment(comment);
    }
}
