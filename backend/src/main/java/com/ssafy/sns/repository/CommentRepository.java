package com.ssafy.sns.repository;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;

import java.util.stream.Stream;

public interface CommentRepository {

    // 댓글 추가
    Comment save(Comment comment);

    Comment findById(Long id);

    void delete(Comment comment);

    Stream<Comment> findListById(Feed feed, int num);

    Stream<Comment> findListById(Feed feed);
}
