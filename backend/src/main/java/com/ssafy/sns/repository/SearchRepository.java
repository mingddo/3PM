package com.ssafy.sns.repository;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.hashtag.Hashtag;
import com.ssafy.sns.domain.newsfeed.Feed;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.user.SimpleUserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository {

    // 검색 키워드를 통해 해쉬태그 찾기
     public List<Hashtag> searchHashtags(String keyword);

     // 찾은 해쉬태그로 게시물 찾기
    public List<Feed> searchFeeds(Hashtag hash);

    // 검색 키워드를 통해 유저 한 명 찾기
    public List<User> searchUsers(String keyword);

    // 검색 키워드를 통해 전체 유저 찾기
    public List<Group> searchGroups(String keyword);

}
