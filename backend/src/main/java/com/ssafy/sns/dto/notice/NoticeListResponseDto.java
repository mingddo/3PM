package com.ssafy.sns.dto.notice;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class NoticeListResponseDto {

    private List<NoticeResponseDto> noticeResponseDtos;
    private int endNum;

    public NoticeListResponseDto(List<NoticeResponseDto> noticeResponseDto, int endNum) {
        this.noticeResponseDtos = noticeResponseDto;
        this.endNum = endNum;
    }
}
