package com.nexters.rezoom.core.global.config.security;

import com.nexters.rezoom.core.domain.member.domain.RezoomMember;
import com.nexters.rezoom.core.domain.member.domain.RezoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by JaeeonJin on 2018-08-02.
 */

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RezoomMemberRepository rezoomMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RezoomMember member = this.rezoomMemberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomUserDetail(member.getUsername(), member.getPassword(), member.getName());
    }
}