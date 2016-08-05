package com.gng.network.dao;

import com.gng.network.enities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@Repository("friendsDao")
public class FriendsJpa implements FriendsDao {
    @Override
    public List<User> getUsersFriends(Integer userId) {
        return null;
    }
}
