package com.gng.network.dao;

import com.gng.network.enities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by georgekankava on 8/5/16.
 */
@Repository("friendsDao")
public class FriendsJpa implements FriendsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsersFriends(Integer userId) {
        try {
            User user = (User)entityManager.createNamedQuery("User.findUserById").setParameter("id", userId).getSingleResult();
            return user.getFriends();
        } catch (NoResultException e) {
            return null;
        }
    }
}
