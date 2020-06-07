package com.nexters.rezoom.core.domain.coverletter.application;

import com.nexters.rezoom.core.domain.coverletter.api.dto.CoverletterDto;
import com.nexters.rezoom.core.domain.coverletter.api.dto.QuestionDto;
import com.nexters.rezoom.core.domain.coverletter.domain.*;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class CoverletterService {

    private final CoverletterRepository coverletterRepository;
    private final HashtagRepository hashTagRepository;

    public Long save(Account account, CoverletterDto.SaveReq req) {
        Coverletter coverletter = Coverletter.newCoverletterBuilder()
                .account(account)
                .companyName(req.getCompanyName())
                .applicationHalf(req.getApplicationHalf())
                .applicationType(req.getApplicationType())
                .applicationYear(Year.of(req.getApplicationYear()))
                .passState(req.getPassState())
                .applicationState(req.getApplicationState())
                .deadline(new Deadline(req.getDeadline()))
                .jobType(req.getJobType())
                .build();

        coverletter.setAccountPK(account.getPK());

        // set questions
        List<Question> questions = new ArrayList<>();
        for (QuestionDto.SaveReq questionReq : req.getQuestions()) {
            questions.add(questionReq.toEntity());
        }
        coverletter.setQuestions(questions);

        // set hashtags
        for (Question question : coverletter.getQuestions()) {
            question.setHashtags(getUpdatedHashtags(question.getHashtags(), account));
        }

        coverletter.checkPassStatus();
        Coverletter savedCoverletter = coverletterRepository.save(coverletter);

        return savedCoverletter.getMyPk();
    }

    // TODO : 문제 있음. hashtag key 문제로 create와 동일하게 작업.
    // TODO : create_date 등의 추가 데이터 누락. 일일히 set 해줘야하는 문제 있음.
    public void update(Account account, Long coverletterId, CoverletterDto.UpdateReq req) {
        Coverletter coverletter = Coverletter.existCoverletterBuilder()
                .myPk(coverletterId)
                .account(account)
                .companyName(req.getCompanyName())
                .applicationYear(Year.of(req.getApplicationYear()))
                .applicationHalf(req.getApplicationHalf())
                .applicationType(req.getApplicationType())
                .passState((req.getPassState()))
                .applicationState(req.getApplicationState())
                .deadline(new Deadline(req.getDeadline()))
                .jobType(req.getJobType())
                .build();

        // set questions
        List<Question> questions = new ArrayList<>();
        for (QuestionDto.UpdateReq questionReq : req.getQuestions()) {
            questions.add(questionReq.toEntity());
        }
        coverletter.setQuestions(questions);

        // set hashtags
        for (Question question : coverletter.getQuestions()) {
            question.setHashtags(getUpdatedHashtags(question.getHashtags(), account));
        }

        coverletter.checkPassStatus();
        coverletterRepository.save(coverletter);
    }

    public CoverletterDto.ViewRes getView(Account account, Long id) {
        Coverletter findCoverletter = getCoverletter(account, id);

        return new CoverletterDto.ViewRes(findCoverletter);
    }

    public Page<CoverletterDto.ViewRes> getList(Account account, Pageable pageable) {
        return coverletterRepository.findAllByAccountPK(pageable, account.getPK())
                .map(CoverletterDto.ViewRes::new);
    }

    public CoverletterDto.ListRes searchByCompanyName(Account account, String companyName) {
        List<Coverletter> coverletters = coverletterRepository.findAllByAccountPKAndCompanyNameStartsWith(account.getPK(), companyName);
        return new CoverletterDto.ListRes(coverletters);
    }

    public void delete(Account account, Long id) {
        Coverletter findCoverletter = getCoverletter(account, id);
        coverletterRepository.delete(findCoverletter);
    }

    private Coverletter getCoverletter(Account account, Long id) {
        Optional<Coverletter> findCoverletter = coverletterRepository.findByAccountPKAndMyPk(id, account.getPK());
        if (!findCoverletter.isPresent()) {
            throw new BusinessException(ErrorType.COVERLETTER_NOT_FOUND);
        }

        return findCoverletter.get();
    }

    /**
     * TODO : Key값이 애매하게 잡혀있어서, Merge를 사용할 수 없음 (개선하기)
     * 중복없이 hashtag를 question에 설정하기 위한 메소드
     * <p>
     * > 중복O : 기존 해시태그 사용
     * > 중복X : 새로운 해시태그 생성
     */
    private Set<Hashtag> getUpdatedHashtags(Set<Hashtag> hashtags, Account account) {
        Set<Hashtag> resultHashtags = new HashSet<>();

        for (Hashtag hashtag : hashtags) {

            Optional<Hashtag> optionalHashtag = hashTagRepository.findByAccountPKAndValue(account.getPK(), hashtag.getValue());
            if (optionalHashtag.isPresent()) {
                resultHashtags.add(optionalHashtag.get());
                continue;
            }

            // 존재하지 않는 태그는 새로 생성한다.
            Hashtag newHashtag = new Hashtag(account, hashtag.getValue());
            hashTagRepository.save(newHashtag);
            resultHashtags.add(newHashtag);
        }

        return resultHashtags;
    }
}
