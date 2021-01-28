package com.ssafy.sns.repository;

import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
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
    public List<Indoor> findList(int num) {
        int readPageCnt = 10;

        List<Indoor> resultList = em.createQuery("SELECT i FROM Indoor i ORDER BY i.createdDate DESC", Indoor.class)
                .setFirstResult(num)
                .setMaxResults(readPageCnt)
                .getResultList();

        return resultList;
    }

    @Override
    public Indoor findOne(Long id) {
        return em.find(Indoor.class, id);
    }

    @Override
    public Long save(Indoor indoor) {
        em.persist(indoor);
        return indoor.getId();
    }

    @Override
    public void remove(Long id) {
        Indoor indoor = em.find(Indoor.class, id);
        em.remove(indoor);
    }

    @Override
    public Long update(Long indoorId, IndoorRequestDto indoorDto) {
        Indoor findIndoor = em.find(Indoor.class, indoorId);
        findIndoor.update(indoorDto.getContent(), indoorDto.getFile(), indoorDto.getTags());
        return findIndoor.getId();
    }
}
