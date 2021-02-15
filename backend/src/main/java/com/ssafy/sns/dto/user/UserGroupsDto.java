package com.ssafy.sns.dto.user;

import lombok.Data;

@Data
public class UserGroupsDto {

    private Long groupId;
    private String groupName;

    public UserGroupsDto(Long id, String name) {
        groupId = id;
        groupName = name;
    }
}
