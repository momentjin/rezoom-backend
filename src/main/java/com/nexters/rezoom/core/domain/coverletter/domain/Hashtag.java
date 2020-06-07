package com.nexters.rezoom.core.domain.coverletter.domain;

import com.nexters.rezoom.core.domain.member.domain.Account;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtag")
@EqualsAndHashCode(of = {"member", "value"})
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(nullable = false)
    private Long accountPK;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER)
    private List<Question> questions;

    public Hashtag(Account account, String value) {
        this.accountPK = account.getPK();
        this.value = value;
        this.questions = new ArrayList<>();
    }

    public Hashtag(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMember(Account account) {
        this.accountPK = account.getPK();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
