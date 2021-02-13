package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.CommentClap;
import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class CommentClapRepositoryImpl implements CommentClapRepository {

    private final EntityManager em;

    @Override
    public Optional<CommentClap> findClap(User user, Comment comment) {
        return em.createQuery("SELECT c " +
                "FROM CommentClap c " +
                "WHERE c.user.id = :userId AND c.comment.id = :commentId", CommentClap.class)
                .setParameter("userId", user.getId())
                .setParameter("commentId", comment.getId())
                .getResultStream()
                .findFirst();
    }

    @Override
    public void delete(CommentClap commentClap) {
        em.remove(commentClap);
    }

    @Override
    public CommentClap save(CommentClap commentClap) {
        em.persist(commentClap);
        return commentClap;
    }

    @Override
    public List<CommentClap> findClapAll(Comment comment) {
        return em.createQuery("SELECT c " +
                "FROM CommentClap c " +
                "WHERE c.comment.id = :commentId", CommentClap.class)
                .setParameter("commentId", comment.getId())
                .getResultList();
    }
}
