package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.CommentClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CommentClapRepository {

    Optional<CommentClap> findClap(User user, Comment comment);

    void delete(CommentClap commentClap);

    CommentClap save(CommentClap commentClap);

    List<CommentClap> findClapAll(Comment comment);
}
