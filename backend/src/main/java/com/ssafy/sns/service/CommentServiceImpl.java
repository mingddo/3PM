package com.ssafy.sns.service;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.comment.CommentDto;
import com.ssafy.sns.dto.comment.CommentRequestDto;
import com.ssafy.sns.dto.comment.CommentResponseDto;
import com.ssafy.sns.repository.CommentRepositoryImpl;
import com.ssafy.sns.repository.FeedRepositoryImpl;
import com.ssafy.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    @Override
    public void modify(CommentRequestDto commentRequestDto, Long userId, Long feedId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        if (feedRepository.findById(feedId) == null) throw new NoSuchElementException();

        Comment comment = commentRepository.findById(commentId);
        if (commentId == null || !comment.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }
        comment.update(commentRequestDto);
    }

    @Override
    public void remove(Long userId, Long feedId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Feed feed = feedRepository.findById(feedId);
        if (feed == null) throw new NoSuchElementException();

        Comment comment = commentRepository.findById(commentId);
        if (commentId == null || !comment.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException();
        }

        user.deleteComment(comment);
        feed.deleteComment(comment);
        commentRepository.delete(comment);
    }

    @Override
    public CommentResponseDto getList(Long feedId, int num) {
        Feed feed = feedRepository.findById(feedId);
        if (feed == null) throw new NoSuchElementException();

        return new CommentResponseDto(commentRepository.findListById(feed, num)
                .map(CommentDto::new)
                .collect(Collectors.toList()),
                num);
    }
}
