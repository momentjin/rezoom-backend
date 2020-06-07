package com.nexters.rezoom.core.domain.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by momentjin@gmail.com on 2019-12-19
 * Github : http://github.com/momentjin
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RezoomMember extends Account {

    public RezoomMember (String username, String name) {
        super(username, name);
    }

    public RezoomMember (String username, String name, String password) {
        super(username, name, password);
    }
}