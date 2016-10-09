package com.gng.network.controller;

import com.gng.network.enities.User;
import com.gng.network.exceptions.EmptyListException;
import com.gng.network.service.FriendsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@Controller
public class FriendsController {

    private static final Logger logger = LoggerFactory.getLogger(FriendsController.class.getName());

    @Inject
    private FriendsService friendsService;

    @RequestMapping("/user-friends")
    public ModelAndView getUserFriends(Integer userId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("friends");
        try {
            List<User> friends = friendsService.getUsersFriends(userId);
            mav.addObject("friendsList", friends);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return mav;
    }
}
