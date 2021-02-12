package com.ssafy.sns.service;

import com.ssafy.sns.dto.comment.CommentRequestDto;

public interface CommentService {

    void write(CommentRequestDto commentRequestDto, Long userId, Long feedId);

    void modify(CommentRequestDto commentRequestDto, Long userId, Long feedId, Long commentId);

    void remove(Long userId, Long feedId, Long commentId);
}
