package com.jiwon.auth.membership.repository;

import com.jiwon.auth.membership.entity.ValidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<ValidToken, String> {
}
