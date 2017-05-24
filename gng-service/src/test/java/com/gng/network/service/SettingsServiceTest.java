package com.gng.network.service;

import com.gng.network.dao.MessageDao;
import com.gng.network.dao.UserDao;
import com.gng.network.enities.User;
import com.gng.network.enities.UserPrivacy;
import com.gng.network.helper.MessageHelper;
import com.gng.network.service.impl.MessageServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    public void testParticipateInGNGNetworkSearch() {
        User user = mock(User.class);
        UserPrivacy userPrivacy = mock(UserPrivacy.class);
        user.setUserPrivacy(userPrivacy);
        when(user.getUserPrivacy()).thenReturn(userPrivacy);
        when(userDao.findUserByUsername("testUsername")).thenReturn(user);
        settingsService.participateInGNGNetworkSearch("testUsername", false);
    }
}
