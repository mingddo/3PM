package com.ssafy.sns.service;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.group.GroupMember;
import com.ssafy.sns.domain.group.MemberRole;
import com.ssafy.sns.domain.notice.NoticeGroup;
import com.ssafy.sns.domain.user.User;
import com.ssafy.sns.dto.group.GroupReqDto;
import com.ssafy.sns.dto.group.GroupCreateResDto;
import com.ssafy.sns.dto.group.GroupResDto;
import com.ssafy.sns.repository.GroupMemberRepository;
import com.ssafy.sns.repository.GroupRepository;
import com.ssafy.sns.repository.NoticeRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final S3Service s3Service;
    private final NoticeRepositoryImpl noticeRepository;

    public GroupCreateResDto create(User user, GroupReqDto reqDto) throws IOException {
        // 이미 그룹 이름이 있다면!!
        if (groupRepository.findByName(reqDto.getName()).isPresent()) {
            return null;
        }

        Group group = groupRepository.save(reqDto.toEntity());
        GroupMember leader = groupMemberRepository.save(new GroupMember(user, group, MemberRole.LEADER));
        group.join(leader);
        return new GroupCreateResDto(group.getId());
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

    public boolean joinGroup(User user, Long groupId) {
        Group group = getGroup(groupId);
        if (group == null) {
            return false;
        }

        // 이미 가입했는 지 확인
        if (!groupMemberRepository.findByUserAndGroup(user, group).isPresent()) {
            groupMemberRepository.save(new GroupMember(user, group, MemberRole.MEMBER));
            noticeRepository.save(new NoticeGroup(groupId, user.getId()));
            return true;
        }
        return false;
    }

    public List<GroupResDto> getGroupList() {
        return groupRepository.findAll()
                .stream()
                .map(GroupResDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public GroupResDto getGroupDetail(Long groupId) {
        return groupRepository.findById(groupId)
                .stream()
                .map(GroupResDto::new)
                .findFirst()
                .get();
    }

    public void saveGroupImg(Long groupId, MultipartFile file) throws IOException {
        String fileName = s3Service.uploadFile(file);
        Group group = getGroup(groupId);
        group.setProfile(fileName);
    }

    public void exitGroup(Long groupId, User user) throws Exception {
        Group group = getGroup(groupId);
        GroupMember groupMember = groupMemberRepository.findByUserAndGroup(user, group).orElseThrow();
        if (groupMember.isLeader()) {
            throw new Exception("그룹장은 탈퇴할 수 없습니다.");
        }

        // 유저 엔티티에서 삭제
        user.removeGroup(groupMember);
        // 그룹 엔티티에서 삭제
        group.removeMember(groupMember);
        // 그룹 멤버 엔티티에서 삭제
        groupMemberRepository.delete(groupMember);
    }

    private GroupMember getGroupMember(User user, Group group) throws Exception {
        return groupMemberRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new Exception("잘못된 요청입니다."));
    }

    public void deleteGroup(Long groupId, User user) throws Exception {
        Group group = getGroup(groupId);
        GroupMember leader = getGroupMember(user, group);
        if (!leader.isLeader()) {
            throw new Exception("권한이 없습니다.");
        }

        List<GroupMember> members = group.getGroupMembers();
        for (GroupMember groupMember : members) {
            groupMember.removeUser();
        }
        groupMemberRepository.deleteAll(members);
        groupRepository.delete(group);
    }

    public void updateGroup(User user, Long groupId, GroupReqDto reqDto) throws Exception {
        Group group = getGroup(groupId);
        GroupMember leader = getGroupMember(user, group);
        if (!leader.isLeader()) {
            throw new Exception("권한이 없습니다.");
        }
        group.updateGroup(reqDto);
    }

}
