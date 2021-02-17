package com.ssafy.sns.dto.group;

import com.ssafy.sns.domain.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class SimpleGroupResDto {

    private Long id;
    private String name;
    private String groupImg;

    public SimpleGroupResDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.groupImg = group.getGroupImg();
    }
}
