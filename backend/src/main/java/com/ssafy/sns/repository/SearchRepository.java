package com.ssafy.sns.repository;

import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Indoor;
import com.ssafy.sns.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository {

    // 검색 키워드를 통해 해쉬태그 찾기
     public List<Hashtag> findHashtag(String keyword);

     // 찾은 해쉬태그로 게시물 찾기
    public List<Indoor> findIndoorAll(Hashtag hash);

    // 검색 키워드를 통해 유저 한 명 찾기
    public User findUserOne(String keyword);

    // 검색 키워드를 통해 전체 유저 찾기
    public List<User> findUserAll(String keyword);

    // filter = 게시물
//    public List<Indoor> searchIndoor(Hashtag tag);

    // filter = 사람
//    public List<User> searchUser(User user);

    // filter = 그륩 Not yet
}
