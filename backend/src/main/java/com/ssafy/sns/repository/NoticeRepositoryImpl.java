package com.ssafy.sns.repository;

import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository{

    private final EntityManager em;

    @Override
    public Notice save(Notice notice) {
        em.persist(notice);
        return notice;
    }

    @Override
    public void delete(Notice notice) {
        em.remove(notice);
    }

    @Override
    public List<Notice> followList(User user) {
        return em.createQuery("select n from NoticeFollow n where n.follow.toUser.id = :user", Notice.class)
                .setParameter("user", user.getId())
                .getResultList();
    }

    @Override
    public List<Notice> commentList(User user) {
        return em.createQuery("select n from NoticeComment n where n.comment.feed.user.id = :user", Notice.class)
                .setParameter("user", user.getId())
                .getResultList();
    }

    @Override
    public List<Notice> feedClabList(User user) {
        return em.createQuery("select n from NoticeFeedClap n where n.feedClap.feed.user.id = :user", Notice.class)
                .setParameter("user", user.getId())
                .getResultList();
    }
}
