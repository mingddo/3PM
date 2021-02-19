package com.ssafy.sns.dto.group;

import com.ssafy.sns.domain.group.Group;
import com.ssafy.sns.domain.group.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class GroupResDto implements Comparable<GroupResDto>{

    private Long id;
    private String name;
    private String groupImg;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int memberCnt;
    private List<GroupMemberResDto> leaders;
    private List<GroupMemberResDto> members;
    private Long leaderId;


    public GroupResDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.groupImg = group.getGroupImg();
        this.description = group.getDescription();
        this.createdDate = group.getCreatedDate();
        this.modifiedDate = group.getModifiedDate();
        this.leaders = group.getGroupMembers().stream()
                .filter(groupMember -> groupMember.getRole().equals(MemberRole.LEADER))
                .map(GroupMemberResDto::new)
                .collect(Collectors.toList());
        this.members = group.getGroupMembers().stream()
                .filter(groupMember -> groupMember.getRole().equals(MemberRole.MEMBER))
                .map(GroupMemberResDto::new)
                .collect(Collectors.toList());
        this.memberCnt = group.getGroupMembers().size();
        this.leaderId = this.leaders.get(0).getId();
    }

    @Override
    public int compareTo(GroupResDto o) {
        return o.createdDate.compareTo(this.createdDate);
    }
}
