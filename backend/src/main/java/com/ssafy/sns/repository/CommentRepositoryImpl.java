package com.ssafy.sns.repository;

import com.ssafy.sns.domain.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
