package com.nexters.rezoom.core.domain.coverletter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByAccountPKAndValue(Long accountPK, String value);
    List<Hashtag> findAllByAccountPK(Long accountPK);
}