package com.gng.network.service;

import com.gng.network.enities.User;

import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
public interface FriendsService {
    List<User> getUsersFriends(Integer userId);
}
