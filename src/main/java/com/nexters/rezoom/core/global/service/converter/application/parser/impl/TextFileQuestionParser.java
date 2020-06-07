package com.nexters.rezoom.core.global.service.converter.application.parser.impl;

import com.nexters.rezoom.core.domain.coverletter.domain.Hashtag;
import com.nexters.rezoom.core.domain.coverletter.domain.Question;
import com.nexters.rezoom.core.global.service.converter.application.parser.QuestionParser;
import com.nexters.rezoom.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 *
 * 텍스트 파일의 내용이 다음과 같아야 정상적으로 migration이 수행된다.
 *
 * Q1. 문항 제목1 (필수)
 * T1. 태그1, 태그2 ... (생략가능)
 * A1. 문항 내용2 (필수)
 * END
 *
 * Q2. 문항 제목2
 * T2. 태그1, 태그2 ...
 * A2. 문항 내용2
 * END
 *
 * ****************
 *
 * 예시)
 * 삼성전자 2019 상반기 신입
 *
 * Q1. 지원동기 및 포부를 말씀해주세요.
 * T1. 지원동기, 포부
 * A1. 삼성전자가 좋아서 지원했습니다. 열심히 하겠습니다. 하하하하하하하
 * 삼성전자가 좋은 두 번째 이유는 ~~~
 * 세번째 이유는 ~~
 * END
 *
 * Q2. 성장과정을 말씀해주세요.
 * T2. 성장과정
 * A2. 부모님의 사랑을 받으며 잘 컸습니다.
 * END
 *
 */
public final class TextFileQuestionParser implements QuestionParser {

    private final static String SUPPORT_FILE_EXTENSION = "txt";

    @Override
    public List<Question> parseQuestions(File file) {

        // 1. load a file
        Scanner scanner = loadFile(file);

        // 2. parse a file's contents
        List<Question> questions = new ArrayList<>();

        String title = "", contents = "";
        Set<Hashtag> hashtags = new HashSet<>();

        int successFlag = 0;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            // 문항 제목
            if (line.startsWith("Q")) {
                successFlag += 1;
                title = line.substring(4);
            }

            // 문항 내용
            else if (line.startsWith("A")) {
                successFlag -= 1;
                StringBuilder sb = new StringBuilder(line.substring(4)).append("\n");

                while (scanner.hasNext()) {
                    String contentsLine = scanner.nextLine();

                    if (contentsLine.equals("END")) break;

                    sb.append(contentsLine).append("\n");
                }

                if (!sb.toString().startsWith("Q"))
                    contents = sb.toString();
            }

            // 태그
            else if (line.startsWith("T")) {
                line = line.substring(4);
                hashtags = Arrays.stream(line.split(", "))
                        .map(Hashtag::new)
                        .collect(Collectors.toSet());
            }

            if (!title.equals("") && !contents.equals("")) {
                Question q = new Question(title, contents);
                q.setHashtags(hashtags);
                questions.add(q);

                // reset
                title = "";
                contents = "";
                hashtags = new HashSet<>();
            }
        }

        if (successFlag != 0)
            throw new IllegalArgumentException("파일 내용이 올바르지 않습니다.");

        // 3. return
        return questions;
    }

    @Override
    public void supports(File file) {

        String fileExtension = FileUtils.getFileExtension(file);
        if (!SUPPORT_FILE_EXTENSION.equals(fileExtension.toLowerCase())) {
            throw new UnsupportedOperationException("txt 파일만 가능합니다.");
        }
    }

    private Scanner loadFile(File file) {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
