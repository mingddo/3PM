package com.ssafy.sns.domain.group;

public enum MemberRole {

    LEADER("leader"), MEMBER("member");

    private String name;

    MemberRole(String name) {
        this.name = name;
    }

    public String getRole() {
        return name.toUpperCase();
    }

    public boolean isLeader(String role) {
        return role.equals(LEADER.getRole());
    }

}
