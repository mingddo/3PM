package com.ssafy.sns.repository;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.follow.Follow;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.notice.NoticeComment;
import com.ssafy.sns.domain.notice.NoticeFeedClap;
import com.ssafy.sns.domain.notice.NoticeFollow;
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
        return em.createQuery("select n from NoticeFollow n where n.follow_id in (select f.id from Follow f where f.toUser.id = :user)", Notice.class)
                .setParameter("user", user.getId())
                .getResultList();
    }

    @Override
    public List<Notice> commentList(User user) {
        return em.createQuery("select n from NoticeComment n where n.comment_id in (select c.id from Comment c where c.feed.user.id = :user)", Notice.class)
                .setParameter("user", user.getId())
                .getResultList();
    }

    @Override
    public List<Notice> feedClabList(User user) {
        return em.createQuery("select n from NoticeFeedClap n where n.feed_clab_id in (select f.id from FeedClap f where f.feed.user.id = :user)", Notice.class)
                .setParameter("user", user.getId())
                .getResultList();
    }
}
