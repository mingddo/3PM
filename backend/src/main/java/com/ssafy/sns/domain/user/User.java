package com.ssafy.sns.domain.user;

import com.ssafy.sns.domain.BaseTimeEntity;

import com.ssafy.sns.domain.clap.CommentClap;
import com.ssafy.sns.domain.group.GroupMember;

import com.ssafy.sns.domain.clap.FeedClap;
import com.ssafy.sns.domain.comment.Comment;
import com.ssafy.sns.domain.newsfeed.Feed;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter // 비추천 코드
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
@Builder
@ToString
@Entity
public class User extends BaseTimeEntity {

    // 유저 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 카카오에서 주는 ID
    @Column(name = "kakao_id", nullable = false, unique = true) // length 입력 안하면 기본이 255
    private Long kakaoId;

    // 유저 닉네임
    @Column(name = "user_nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "nickname_split", nullable = false, unique = true)
    private String nicknameSplit;

    // 유저 소개
    @Column(name = "user_introduce")
    private String introduce;

    // 유저 이미지
    @Column(name = "user_img")
    private String img;

    // 유저 설정 정보
    @Embedded
    private UserConfig userConfig;


    // 유저가 속한 그룹 리스트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers = new ArrayList<>();

    // 그룹 탈퇴
    public void removeGroup(GroupMember groupMember) {
        this.groupMembers.remove(groupMember);
    }

    //그룹 가입
    public void addGroup(GroupMember groupMember) {
        this.groupMembers.add(groupMember);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedClap> feedClapList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentClap> commentClaps = new ArrayList<>();

    public void addFeed(Feed feed) {
        if (feeds == null) feeds = new ArrayList<>();
        feeds.add(feed);
        feed.setUser(this);
    }

    public void deleteFeed(Feed feed) {
        feeds.remove(feed);
        feed.setUser(null);

    }

    public void addFeedClap(FeedClap feedClap) {
        if (feedClapList == null) feedClapList = new ArrayList<>();
        feedClapList.add(feedClap);
        feedClap.setUser(this);
    }

    public void deleteFeedClap(FeedClap feedClap) {
        feedClapList.remove(feedClap);
        feedClap.setUser(null);

    }

    public void addComment(Comment comment) {
        if (commentList == null) commentList = new ArrayList<>();
        commentList.add(comment);
        comment.setUser(this);
    }

    public void deleteComment(Comment comment) {
        commentList.remove(comment);
        comment.setUser(null);
    }

    public void addCommentClap(CommentClap commentClap) {
        if (commentClaps == null) commentClaps = new ArrayList<>();
        commentClaps.add(commentClap);
        commentClap.setUser(this);
    }

    public void deleteCommentClap(CommentClap commentClap) {
        commentClaps.remove(commentClap);
        commentClap.setUser(null);
    }
}
