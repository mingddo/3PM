package com.ssafy.sns.repository;

import com.ssafy.sns.domain.comment.Comment;

public interface CommentRepository {

    // 댓글 추가
    Comment save(Comment comment);

    Comment findById(Long id);

    void delete(Comment comment);
}
