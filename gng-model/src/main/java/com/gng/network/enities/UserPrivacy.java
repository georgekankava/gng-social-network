package com.gng.network.enities;

import com.gng.network.enums.UserPrivacyEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by georgekankava on 23.05.17.
 */
@Entity
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserPrivacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean participateInNetworkSearch;

    @Column(name = "add_user_as_friend_strategy")
    @Enumerated(value = EnumType.STRING)
    private UserPrivacyEnum addUserAsFriendStrategy;

    @OneToOne
    private User user;

}
