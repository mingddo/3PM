package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepository {

    private final EntityManager em;

    @Override
    public List<Hashtag> make(List<String> tags) {
        List<Hashtag> result = new ArrayList<>();

        for (String tag : tags) {
            List<Hashtag> resultTag = em.createQuery("SELECT h FROM Hashtag h WHERE h.tagName = :tag", Hashtag.class)
                    .setParameter("tag", tag)
                    .getResultList();
            if (resultTag.isEmpty()) {
                Hashtag hashtag = new Hashtag(tag);
                em.persist(hashtag);
                result.add(hashtag);
            } else {
                result.add(resultTag.get(0));
            }
        }

        return result;
    }
}
