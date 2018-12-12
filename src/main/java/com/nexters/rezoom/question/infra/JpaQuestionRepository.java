package com.nexters.rezoom.question.infra;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Service
public class JpaQuestionRepository implements QuestionRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Question findByKey(long questionId, Member member) {
        TypedQuery<Question> query = em.createQuery(
                "SELECT q FROM Question q " + "INNER JOIN q.coverletter c " +
                        "WHERE q.id =:id AND c.member =:member", Question.class);
        query.setParameter("id", questionId);
        query.setParameter("member", member);

        List<Question> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

}
