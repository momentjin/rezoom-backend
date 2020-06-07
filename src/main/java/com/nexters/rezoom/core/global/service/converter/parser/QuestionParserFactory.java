package com.nexters.rezoom.core.global.service.converter.parser;

import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import com.nexters.rezoom.core.global.service.converter.parser.impl.TextFileQuestionParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public
class QuestionParserFactory {

    private final static String TXT = "txt";

    public static TextFileQuestionParser createConverterByExtension(String extension) {
        if (TXT.equals(extension)) {
            return new TextFileQuestionParser();
        }

        throw new BusinessException(ErrorType.UNSURPPOTED_FILE_EXTENTION);
    }
}
