package com.ssafy.sns.domain.group;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.newsfeed.Insider;
import com.ssafy.sns.dto.group.GroupReqDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Insider> insiders = new ArrayList<>();

    private String name;
    private String description;
    private String groupImg;

    @Builder
    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void join(GroupMember groupMember) {
        groupMembers.add(groupMember);
        groupMember.joinGroup(this);
    }

    public void setProfile(String fileName) {
        this.groupImg = fileName;
    }

    public void addFeed(Insider insider) {
        this.insiders.add(insider);
    }

    public void removeMember(GroupMember groupMember) {
        this.groupMembers.remove(groupMember);
    }

    public void updateGroup(GroupReqDto reqDto) {
        this.name = reqDto.getName();
        this.description = reqDto.getDescription();
    }

}
