package com.gng.network.service.impl;

import com.gng.network.dao.FriendsDao;
import com.gng.network.enities.User;
import com.gng.network.exceptions.EmptyListException;
import com.gng.network.service.FriendsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@Service("fsriendsService")
public class FriendsServiceImpl implements FriendsService {

    private static final Logger logger = LoggerFactory.getLogger(FriendsServiceImpl.class);

    @Inject
    private FriendsDao friendsDao;

    @Override
    public List<User> getUsersFriends(Integer userId) throws EmptyListException {
        try {
            return friendsDao.getUsersFriends(userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
