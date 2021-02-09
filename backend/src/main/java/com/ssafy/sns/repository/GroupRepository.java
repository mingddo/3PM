package com.ssafy.sns.repository;

import com.ssafy.sns.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);

    Optional<Group> findById(Long id);

    List<Group> findAllByNameContaining(String name);
}
