package com.back.domain.member.member.entity;

import com.back.domain.member.member.common.MemberRole;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
public class Member extends BaseEntity {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String nickname;
    private boolean isBanned;
    private MemberRole role;
    private String profileImageUrl;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
