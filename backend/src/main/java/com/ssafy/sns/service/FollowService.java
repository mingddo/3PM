package com.ssafy.sns.service;

import com.ssafy.sns.domain.follow.Follow;

import java.util.ArrayList;
import java.util.List;

public interface FollowService {

    public int fromMeToOthers(Long id);

    public int toMeFromOthers(Long id);

    public List<Long> fromMeToOthersList(Long id);

    public List<Long> toMeFromOthersList(Long id);
}
