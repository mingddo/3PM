package com.ssafy.sns.repository;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.group.GroupMember;
import com.ssafy.sns.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findAllByUser(User user);

    List<GroupMember> findAllByGroup(Group group);

    Optional<GroupMember> findByUserAndGroup(User user, Group group);

}
