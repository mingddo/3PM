package com.ssafy.sns.dto.group;

import com.ssafy.sns.domain.group.Group;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupReqDto {

    @ApiModelProperty(position = 1)
    private String name;

    @ApiModelProperty(position = 2)
    private String description;

    public Group toEntity() {
        return Group.builder()
                .description(description)
                .name(name)
                .build();
    }
}
