package com.gng.network.service;

import com.gng.network.dao.MessageDao;
import com.gng.network.dao.UserDao;
import com.gng.network.enities.User;
import com.gng.network.enities.UserPrivacy;
import com.gng.network.enums.UserPrivacyEnum;
import com.gng.network.exceptions.PasswordDoNotMatchException;
import com.gng.network.exceptions.ServiceException;
import com.gng.network.helper.MessageHelper;
import com.gng.network.service.impl.MessageServiceImpl;
import com.gng.network.utils.ApplicationUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.*;

 /**
 * Created by georgekankava on 24.05.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private SettingsService settingsService;

    @Test
    public void testValidateAndChangeUserPassword() throws PasswordDoNotMatchException, ServiceException, UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = mock(User.class);
        when(user.getPassword()).thenReturn(ApplicationUtils.passwordDigest("testpassword"));
        when(userDao.findUserById(1)).thenReturn(user);
        settingsService.validateAndChangeUserPassword(1, "testpassword", "testpassword");
    }

    @Test(expected = PasswordDoNotMatchException.class)
    public void testValidateAndChangeUserPasswordWithWrongPassword() throws PasswordDoNotMatchException, ServiceException, UnsupportedEncodingException, NoSuchAlgorithmException {
        User user = mock(User.class);
        when(user.getPassword()).thenReturn(ApplicationUtils.passwordDigest("testpassword1"));
        when(userDao.findUserById(1)).thenReturn(user);
        settingsService.validateAndChangeUserPassword(1, "testpassword", "testpassword");
    }

    @Test
    public void testParticipateInGNGNetworkSearch() {
        User user = mock(User.class);
        UserPrivacy userPrivacy = mock(UserPrivacy.class);
        user.setUserPrivacy(userPrivacy);
        when(user.getUserPrivacy()).thenReturn(userPrivacy);
        when(userDao.findUserByUsername("testUsername")).thenReturn(user);
        settingsService.participateInGNGNetworkSearch("testUsername", false);
    }

    @Test
    public void testUpdateUserLookupPrivacy() {
        User user = mock(User.class);
        UserPrivacy userPrivacy = mock(UserPrivacy.class);
        when(user.getUserPrivacy()).thenReturn(userPrivacy);
        userPrivacy.setUserLookupStrategy(UserPrivacyEnum.FRIENDS);
        when(userDao.findUserByUsername("testUsername")).thenReturn(user);
        settingsService.updateUserLookupPrivacy("testUsername", UserPrivacyEnum.FRIENDS);
    }
}
