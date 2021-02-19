package com.ssafy.sns.repository;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    @Override
    public List<Notice> groupList(Group group) {
        return em.createQuery("select ng from NoticeGroup ng where ng.group_id in (select g.group.id from GroupMember g where g.group.id = :group)", Notice.class)
                .setParameter("group", group.getId())
                .getResultList();
    }

    @Override
    public List<Group> groupLeader(User user) {
        return em.createQuery("select g from Group g where g.id in (select gm.group.id from GroupMember gm where gm.user.id = :user and gm.role = 'LEADER')", Group.class)
                .setParameter("user", user.getId())
                .getResultList();
    }
}
