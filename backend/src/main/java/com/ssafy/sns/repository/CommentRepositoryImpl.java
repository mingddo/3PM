package com.ssafy.sns.repository;

import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Select;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }

    @Override
    public Stream<Comment> findListById(Feed feed, int num) {
        int readPageCnt = 10;

        return em.createQuery("SELECT c " +
                "FROM Comment c " +
                "WHERE c.feed.id = :feedId " +
                "ORDER BY c.createdDate DESC", Comment.class)
                .setParameter("feedId", feed.getId())
                .setFirstResult(num)
                .setMaxResults(readPageCnt)
                .getResultStream();
    }

    @Override
    public Stream<Comment> findListById(Feed feed) {

        return em.createQuery("SELECT c " +
                "FROM Comment c " +
                "WHERE c.feed.id = :feedId ", Comment.class)
                .setParameter("feedId", feed.getId())
                .getResultStream();
    }
}
