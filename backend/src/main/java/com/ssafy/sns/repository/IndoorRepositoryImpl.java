package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.newsfeed.FeedRequestDto;
import com.ssafy.sns.dto.newsfeed.IndoorRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IndoorRepositoryImpl implements FeedRepository {

    private final EntityManager em;

    @Override
    public List<Feed> findMyList(Long id, int num) {
        int readPageCnt = 10;

        return em.createQuery("SELECT f FROM Feed f WHERE f.user.id = :id ORDER BY f.createdDate DESC", Feed.class)
                .setParameter("id", id)
                .setFirstResult(num)
                .setMaxResults(readPageCnt)
                .getResultList();
    }

    @Override
    public List<Feed> findList(int num) {
        int readPageCnt = 10;

        return em.createQuery("SELECT f FROM Feed f ORDER BY f.createdDate DESC", Feed.class)
                .setFirstResult(num)
                .setMaxResults(readPageCnt)
                .getResultList();
    }

    @Override
    public Feed findById(Long id) {
        return em.find(Indoor.class, id);
    }

    @Override
    public Feed save(FeedRequestDto indoorRequestDto, User user) {
        Indoor indoor = Indoor.builder()
                .content(indoorRequestDto.getContent())
                .user(user)
                .test(((IndoorRequestDto)indoorRequestDto).getTest())
                .build();

        em.persist(indoor);

        return indoor;
    }

    @Override
    public void remove(Long id) {
        Indoor indoor = em.find(Indoor.class, id);
        em.remove(indoor);
    }

    @Override
    public void update(Long feedId, FeedRequestDto feedRequestDto) {
        Indoor indoor = (Indoor) findById(feedId);
        indoor.update(feedRequestDto.getContent(), ((IndoorRequestDto)feedRequestDto).getTest());
    }
}
