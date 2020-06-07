package com.nexters.rezoom.core.global.service.converter.application.parser;

import com.nexters.rezoom.core.domain.coverletter.domain.Question;

import java.io.File;
import java.util.List;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
public interface QuestionParser {

    List<Question> parseQuestions(File file);

    void supports(File file);
}
