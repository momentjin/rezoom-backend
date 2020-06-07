package com.nexters.rezoom.core.domain.coverletter.application;

import com.nexters.rezoom.core.domain.coverletter.domain.Coverletter;
import com.nexters.rezoom.core.domain.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.core.global.service.converter.CoverletterConverter;
import com.nexters.rezoom.core.global.service.converter.parser.QuestionParser;
import com.nexters.rezoom.core.global.service.converter.parser.QuestionParserFactory;
import com.nexters.rezoom.core.global.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@Service
@RequiredArgsConstructor
public class ConverterService {

    private final CoverletterRepository coverletterRepository;

    public void convertFileToCoverletter(Account account, MultipartFile[] files) {
        for (MultipartFile multipartFile : files) {
            convertAndSaveCoverletter(account, multipartFile);
        }
    }

    private void convertAndSaveCoverletter(Account account, MultipartFile multipartFile) {

        Coverletter newCoverletter = this.convert(account, multipartFile);
        this.save(newCoverletter);
    }

    private Coverletter convert(Account account, MultipartFile multipartFile) {

        File file = FileUtils.convertFile(multipartFile);
        String fileExtension = FileUtils.getFileExtension(file);
        QuestionParser questionParser = QuestionParserFactory.createConverterByExtension(fileExtension);

        CoverletterConverter converter = new CoverletterConverter(file, questionParser);
        return converter.convert(account);
    }

    private void save(Coverletter coverletter) {
        this.coverletterRepository.save(coverletter);
    }
}
