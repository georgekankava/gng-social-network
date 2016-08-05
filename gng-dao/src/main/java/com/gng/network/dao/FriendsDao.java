package com.gng.network.dao;

import com.gng.network.enities.User;

import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
public interface FriendsDao {
    List<User> getUsersFriends(Integer userId);
}
