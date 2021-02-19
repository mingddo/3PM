package com.ssafy.sns.domain.group;

import com.ssafy.sns.domain.BaseTimeEntity;
import com.ssafy.sns.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class GroupMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public GroupMember(User user, Group group, MemberRole role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }


    public void joinGroup(Group group) {
        this.group = group;
    }

    public boolean isLeader() {
        return this.role.equals(MemberRole.LEADER);
    }

    public void changeRole(MemberRole role) {
        this.role = role;
    }

    public void removeUser() {
        this.user.removeGroup(this);
    }
}
