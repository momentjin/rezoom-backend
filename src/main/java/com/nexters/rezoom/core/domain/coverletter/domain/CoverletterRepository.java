package com.nexters.rezoom.core.domain.coverletter.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

/**
 * Created by momentjin@gmail.com on 2019-10-07
 * Github : http://github.com/momentjin
 */

@Repository
public interface CoverletterRepository extends JpaRepository<Coverletter, Long> {

    Optional<Coverletter> findByAccountPKAndMyPk(Long accountPK, Long myPK);
    Page<Coverletter> findAllByAccountPK(Pageable pageable, Long accountPK);
    List<Coverletter> findAllByDeadlineGreaterThanEqual(Deadline deadline);
    List<Coverletter> findAllByAccountPKAndCompanyNameStartsWith(Long accountPK, String companyName);
}
