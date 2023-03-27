package com.clone.instagram.domain.authentication.repository;

import com.clone.instagram.domain.authentication.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByUserId(Long userId);
}
