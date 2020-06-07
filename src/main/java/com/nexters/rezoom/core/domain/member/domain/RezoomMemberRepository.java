package com.nexters.rezoom.core.domain.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RezoomMemberRepository extends JpaRepository<RezoomMember, Long> {
    Optional<RezoomMember> findByUsername(String username);
}
