package com.ssafy.sns.repository;
import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.notice.Notice;
import com.ssafy.sns.domain.user.User;

import java.util.List;


public interface NoticeRepository {

    Notice save(Notice notice);

    void delete(Notice notice);

    List<Notice> followList(User user);

    List<Notice> commentList(User user);

    List<Notice> feedClabList(User user);

    List<Notice> groupList(Group group);

    List<Group> groupLeader(User user);
}
