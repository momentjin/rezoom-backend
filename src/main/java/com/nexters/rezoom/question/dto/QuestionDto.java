package com.nexters.rezoom.question.dto;

import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.hashtag.dto.HashtagDto;
import com.nexters.rezoom.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {
        @NotEmpty
        private String title;

        @NotEmpty
        private String contents;

        private Set<HashtagDto.SaveReq> hashtags = new HashSet<>();

        public Question toEntity() {
            Question question = new Question(title, contents);
            question.setHashtags(hashtags.stream().map(HashtagDto.SaveReq::toEntity).collect(Collectors.toSet()));
            return question;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        @Positive
        private long id;

        @NotEmpty
        private String title;

        @NotEmpty
        private String contents;

        private Set<HashtagDto.SaveReq> hashtags = new HashSet<>();

        public boolean isNew() {
            return this.id == 0;
        }

        public Question toEntity() {
            Question question = new Question(id, title, contents);
            question.setHashtags(hashtags.stream().map(HashtagDto.SaveReq::toEntity).collect(Collectors.toSet()));
            return question;
        }
    }

    @Getter
    public static class ViewRes {
        private long id;
        private long coverletterId;
        private String companyName;
        private String title;
        private String contents;
        //        private Set<HashtagDto.ViewRes> hashtags;
        private Set<String> hashtags;

        public ViewRes(long id, long coverletterId, String companyName, String title, String contents, Set<String> hashtags) {
            this.id = id;
            this.coverletterId = coverletterId;
            this.companyName = companyName;
            this.title = title;
            this.contents = contents;
            this.hashtags = hashtags;
        }

        public ViewRes(Question question) {
            this.id = question.getId();
            this.coverletterId = question.getCoverletter().getId();
            this.companyName = question.getCoverletter().getCompanyName();
            this.title = question.getTitle();
            this.contents = question.getContents();
            this.hashtags = question.getHashtags().stream().map(Hashtag::getValue).collect(Collectors.toSet());
        }
    }

}
