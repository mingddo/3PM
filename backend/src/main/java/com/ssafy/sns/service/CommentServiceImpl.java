package com.ssafy.sns.service;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.notice.NoticeComment;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.comment.CommentDto;
import com.ssafy.sns.dto.comment.CommentRequestDto;
import com.ssafy.sns.dto.comment.CommentResponseDto;
import com.ssafy.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryImpl commentRepository;
    private final FeedRepositoryImpl feedRepository;
    private final UserRepository userRepository;
    private final NoticeRepositoryImpl noticeRepository;
    private final CommentClapRepositoryImpl commentClapRepository;

    @Override
    public void write(CommentRequestDto commentRequestDto, Long userId, Long feedId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Feed feed = feedRepository.findById(feedId);

        Comment comment = commentRepository.save(new Comment(commentRequestDto));
        user.addComment(comment);
        feed.addComment(comment);
        noticeRepository.save(new NoticeComment(comment.getId()));
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
    public CommentResponseDto getList(Long userId, Long feedId, int num) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Feed feed = feedRepository.findById(feedId);
        if (feed == null) throw new NoSuchElementException();
        return new CommentResponseDto(commentRepository.findListById(feed, num)
                .map(comment -> new CommentDto(comment,
                        commentClapRepository.findClapAll(comment).size(),
                        commentClapRepository.findClap(user, comment).isPresent()))
                .collect(Collectors.toList()), num);
    }
}
