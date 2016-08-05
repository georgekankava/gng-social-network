package com.gng.network.service.impl;

import com.gng.network.dao.FriendsDao;
import com.gng.network.enities.User;
import com.gng.network.service.FriendsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@Service("fsriendsService")
public class FriendsServiceImpl implements FriendsService {

    @Inject
    private FriendsDao friendsDao;

    @Override
    public List<User> getUsersFriends(Integer userId) {
        return null;
    }
}
