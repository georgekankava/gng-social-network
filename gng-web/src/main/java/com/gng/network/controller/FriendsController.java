package com.gng.network.controller;

import com.gng.network.enities.User;
import com.gng.network.exceptions.EmptyListException;
import com.gng.network.service.FriendsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@RestController
public class FriendsController {

    @Inject
    private FriendsService friendsService;

    @RequestMapping("/user-friends")
    public List<User> getUserFriends(Integer userId) {
        try {
            return friendsService.getUsersFriends(userId);
        } catch (EmptyListException e) {
            return Collections.emptyList();
        }
    }
}
