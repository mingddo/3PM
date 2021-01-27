package com.ssafy.sns.repository;

import com.ssafy.sns.domain.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // Entity 타입, Entity 식별자 타입 (id)

    Optional<User> findByEmail(String email);


}
