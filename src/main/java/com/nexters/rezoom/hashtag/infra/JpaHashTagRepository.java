package com.nexters.rezoom.hashtag.infra;

import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Service
public class JpaHashTagRepository implements HashTagRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public HashTag findByKey(Member member, String value) {
        // TODO : org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: HashTag.questions, could not initialize proxy - no Session
        TypedQuery<HashTag> query = em.createQuery("SELECT h FROM HashTag h WHERE h.member =:member AND h.value =:value", HashTag.class);
        query.setParameter("member", member);
        query.setParameter("value", value);

        List<HashTag> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<HashTag> findAll(Member member) {
        TypedQuery<HashTag> query = em.createQuery("SELECT h FROM HashTag h WHERE h.member =:member", HashTag.class);
        query.setParameter("member", member);

        return query.getResultList();
    }

    @Override
    public void delete(HashTag hashTag) {
        em.remove(hashTag);
    }

}
