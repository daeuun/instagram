package com.clone.instagram.domain.user.repository;

import com.clone.instagram.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdAndDeleted(String name, boolean deleted);

}
