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
    public NoticeFeedClap findFeedClap(FeedClap feedClap) {
        return em.createQuery("select nfc from NoticeFeedClap nfc where nfc.feedClap.id = :feedClap", NoticeFeedClap.class)
                .setParameter("feedClap", feedClap.getId())
                .getSingleResult();
    }

    @Override
    public NoticeComment findComment(Comment comment) {
        return em.createQuery("select nc from NoticeComment nc where nc.comment.id = :comment", NoticeComment.class)
                .setParameter("comment", comment.getId())
                .getSingleResult();
    }

    @Override
    public NoticeFollow findFollow(Follow follow) {
        NoticeFollow noticeFollow = em.createQuery("select nf from NoticeFollow nf where nf.follow.id = :follow", NoticeFollow.class)
                .setParameter("follow", follow.getId())
                .getSingleResult();
        em.flush();
        return noticeFollow;
    }

    @Override
    public List<Notice> followList(User user) {
        return null;
    }

    @Override
    public List<Notice> commentList(User user) {
        return null;
    }

    @Override
    public List<Notice> feedClabList(User user) {
        return null;
    }

//    @Override
//    public List<Notice> followList(User user) {
//        return em.createQuery("select n from NoticeFollow n where (select )n.follow_id = :user", Notice.class)
//                .setParameter("user", user.getId())
//                .getResultList();
//    }
//
//    @Override
//    public List<Notice> commentList(User user) {
//        return em.createQuery("select n from NoticeComment n where n.comment.feed.user.id = :user", Notice.class)
//                .setParameter("user", user.getId())
//                .getResultList();
//    }
//
//    @Override
//    public List<Notice> feedClabList(User user) {
//        return em.createQuery("select n from NoticeFeedClap n where n.feedClap.feed.user.id = :user", Notice.class)
//                .setParameter("user", user.getId())
//                .getResultList();
//    }
}
