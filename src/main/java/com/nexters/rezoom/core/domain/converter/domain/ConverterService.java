package com.nexters.rezoom.core.domain.converter.domain;

import com.nexters.rezoom.core.domain.coverletter.domain.Coverletter;
import com.nexters.rezoom.core.domain.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.core.domain.member.domain.Account;
import com.nexters.rezoom.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@Service
public class ConverterService {

    private final CoverletterRepository coverletterRepository;

    public ConverterService(CoverletterRepository coverletterRepository) {
        this.coverletterRepository = coverletterRepository;
    }

    public void convertFileToCoverletter(Account account, MultipartFile[] files) {
        for (MultipartFile multipartFile : files) {
            saveFile(account, multipartFile);
        }
    }

    private void saveFile(Account account, MultipartFile multipartFile) {
        File file = null;

        try {
            file = FileUtils.convertFile(multipartFile);
            String fileExtension = FileUtils.getFileExtension(file);

            CoverletterConverter converter = ConverterFactory.createConverterByExtension(fileExtension, file);
            Coverletter coverletter = converter.convert(account);
            coverletter.setAccountPK(account.getPK());

            coverletterRepository.save(coverletter);
        } finally {
            if (file != null) file.delete();
        }
    }
}
