package com.clone.instagram.domain.user.repository;

import com.clone.instagram.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmailAndDeleted(String email, boolean deleted);

    Users findByIdAndDeleted(Long id, boolean deleted);

    Optional<Users> findByNicknameAndEmailNotAndDeleted(String nickname, String email, boolean deleted);
}
