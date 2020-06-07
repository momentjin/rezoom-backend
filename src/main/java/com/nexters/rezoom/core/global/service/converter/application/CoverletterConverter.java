package com.nexters.rezoom.core.global.service.converter.application;

import com.nexters.rezoom.core.domain.coverletter.domain.*;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.global.service.converter.application.parser.QuestionParser;
import com.nexters.rezoom.util.FileUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.time.Year;
import java.util.List;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 * <p>
 * 파일 to Coverletter를 위한 파일명 규칙 -> "구글_2019_상반기_신입"
 */
public class CoverletterConverter {

    private static final int COVERLETTER_INFO_SIZE = 4;
    private static final String COVERLETTER_INFO_SEPARATOR = " ";

    private static final int COMPANY_NAME_IDX = 0;
    private static final int APPLICATION_YEAR_IDX = 1;
    private static final int APPLICATION_HALF_IDX = 2;
    private static final int APPLICATION_TYPE_IDX = 3;

    private File file;
    private QuestionParser questionParser;

    public CoverletterConverter(File file, QuestionParser questionParser) {

        validate(file);
        this.file = file;
        this.questionParser = questionParser;
    }

    public Coverletter convert(Member member) {

        Coverletter coverletter = this.createCoverletter(member);
        List<Question> questions = this.questionParser.parseQuestions(this.file);
        coverletter.setQuestions(questions);

        return coverletter;
    }

    private void validate(File file) {

        checkFileExist(file);
        checkFileExtension(file);
        checkFileNameFormat(file);
    }

    private void checkFileExist(File file) {

        if (!file.exists())
            throw new IllegalArgumentException("파일이 존재하지 않습니다");
    }

    private void checkFileExtension(File file) {

        this.questionParser.supports(file);
    }

    private void checkFileNameFormat(File file) {

        String fileName = FileUtils.getFileName(file);
        String[] fileInfos = this.getCoverletterInfos(fileName);

        if (fileName.length() != COVERLETTER_INFO_SIZE) {
            throw new IllegalArgumentException("파일명이 올바르지 않습니다.");
        }

        for (String fileInfo : fileInfos) {
            if (Strings.isEmpty(fileInfo))
                throw new IllegalArgumentException("파일명이 올바르지 않습니다");
        }
    }

    private Coverletter createCoverletter(Member member) {

        String[] coverletterInfos = this.getCoverletterInfos(FileUtils.getFileName(file));

        String companyName = coverletterInfos[COMPANY_NAME_IDX];
        int applicationYear = Integer.parseInt(coverletterInfos[APPLICATION_YEAR_IDX]);
        ApplicationHalf applicationHalf = ApplicationHalf.valueOf(coverletterInfos[APPLICATION_HALF_IDX]);
        ApplicationType applicationType = ApplicationType.valueOf(coverletterInfos[APPLICATION_TYPE_IDX]);

        return Coverletter.newCoverletterBuilder()
                .member(member)
                .companyName(companyName)
                .applicationType(applicationType)
                .applicationHalf(applicationHalf)
                .applicationState(ApplicationState.ETC)
                .applicationYear(Year.of(applicationYear))
                .passState(PassState.ETC)
                .build();
    }

    private String[] getCoverletterInfos(String fileName) {
        return fileName.split(COVERLETTER_INFO_SEPARATOR);
    }
}
