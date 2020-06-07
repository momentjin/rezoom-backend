package com.nexters.rezoom.core.domain.coverletter.infra;

import com.mysema.query.jpa.impl.JPAQuery;
import com.nexters.rezoom.core.domain.coverletter.domain.Question;
import com.nexters.rezoom.core.domain.coverletter.domain.QuestionRepository;
import com.nexters.rezoom.core.domain.member.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.nexters.rezoom.core.domain.coverletter.domain.QQuestion.question;

@Transactional
@Repository
public class JpaQuestionRepository implements QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    public Question findByKey(Long questionId, Account account) {
        JPAQuery query = new JPAQuery(em);

        return query.from(question)
                .where(question.id.eq(questionId).and(question.coverletter.accountPK.eq(account.getPK())))
                .uniqueResult(question);
    }

    @Override
    public Page<Question> findAllByAccountPK(Pageable pageable, Long accountPK) {

        List<Question> questions = new JPAQuery(em)
                .from(question)
                .where(question.coverletter.accountPK.eq(accountPK))
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .list(question);

        long countOfAllQuestion = new JPAQuery(em)
                .from(question)
                .where(question.coverletter.accountPK.eq(accountPK))
                .count();

        return new PageImpl<>(questions, pageable, countOfAllQuestion);
    }
}
