package com.gng.network.controller;

import com.gng.network.enities.User;
import com.gng.network.service.FriendsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@Controller
public class FriendsController {

    @Inject
    private FriendsService friendsService;

    @RequestMapping("/user-friends")
    public List<User> getUserFriends(Integer userId) {

        return null;
    }
}
