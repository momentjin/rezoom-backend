package com.nexters.rezoom.core.domain.member.domain;

import com.nexters.rezoom.core.domain.notification.domain.NotificationSetting;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
@EqualsAndHashCode(of = {"id"})
public class Member {

    @Id
    @Column(name = "member_id")
    protected String id;

    @Setter
    @Column(name = "name")
    protected String name;

    @Setter
    @Column(name = "password")
    protected String password;

    @Setter
    @Column(name = "motto")
    protected String motto;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "member")
    private List<NotificationSetting> notificationSettings;

    public Member(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.notificationSettings = new ArrayList<>();
        this.notificationSettings.add(NotificationSetting.createDefaultSetting(this));
    }

    public void updateMemberInfo(String name, String motto) {
        this.name = name;
        this.motto = motto;
    }
}