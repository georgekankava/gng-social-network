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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "add_user_as_friend_strategy")
    private UserPrivacyEnum addUserAsFriendStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_friends_list_view_strategy")
    private UserPrivacyEnum userFriendsListViewStrategy;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_post_view_strategy")
    private UserPrivacyEnum userPostViewStrategy;

    @OneToOne
    private User user;

}
