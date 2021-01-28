package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.IndoorHashtag;
<<<<<<< HEAD
import com.ssafy.sns.domain.newsfeed.Indoor;
=======
>>>>>>> feature/mypage_BE
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndoorHashtagRepository extends JpaRepository<IndoorHashtag, Long> {

<<<<<<< HEAD
    List<IndoorHashtag> findIndoorHashtagByHashtagTagName(String tagName);
=======
    List<IndoorHashtag> findAllByIndoorId(Long indoorId);

>>>>>>> feature/mypage_BE
}
