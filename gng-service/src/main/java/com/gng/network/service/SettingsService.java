package com.gng.network.service;

import com.gng.network.dao.UserDao;
import com.gng.network.enities.User;
import com.gng.network.exceptions.PasswordDoNotMatchException;
import com.gng.network.exceptions.ServiceException;
import com.gng.network.utils.ApplicationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by georgekankava on 10.05.17.
 */
@Service
public class SettingsService {

    @Inject
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void validateAndChangeUserPassword(Integer userId, String currentPassword, String newPassword) throws PasswordDoNotMatchException, ServiceException {
        try {
            User user = userDao.findUserById(userId);
            if(!ApplicationUtils.validatePasswordMatches(ApplicationUtils.passwordDigest(currentPassword), user.getPassword())) {
                throw new PasswordDoNotMatchException("Your current password is not correct. Please try again.", "reset.password.page.current.password.incorrect.message");
            }
            user.setPassword(ApplicationUtils.passwordDigest(newPassword));
            userDao.updateUser(user);

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(e.getMessage(), "general.purpose.error.on.server.message");
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException(e.getMessage(), "general.purpose.error.on.server.message");
        }
    }

}