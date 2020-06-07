package com.nexters.rezoom.core.domain.coverletter.domain;

import com.nexters.rezoom.core.domain.member.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {

    Question findByKey(Long questionId, Account account);
    Page<Question> findAllByAccountPK(Pageable pageable, Long accountPK);
}
