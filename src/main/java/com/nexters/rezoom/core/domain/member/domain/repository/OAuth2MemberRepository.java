package com.nexters.rezoom.core.domain.member.domain.repository;

import com.nexters.rezoom.core.domain.member.domain.OAuth2Info;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by momentjin@gmail.com on 2020-06-07
 * Github : http://github.com/momentjin
 */
public interface OAuth2MemberRepository extends JpaRepository<OAuth2Info, Long> {
}
