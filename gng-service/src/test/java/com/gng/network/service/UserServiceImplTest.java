package com.gng.network.service;

import com.gng.network.dao.UserDao;
import com.gng.network.enities.User;
import com.gng.network.exceptions.PasswordNotMatchException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.json.response.UsersResponseJson;
import com.gng.network.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by georgekankava on 11/22/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private String username = "testusername";
    private String password = "testpassword";
    private String lastname = "testLastName";

    @Mock
    private UserDao userDao;

    @Mock
    private UserHelper helper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserServiceImpl userService;

    private List userResponseJson = Arrays.asList(new UsersResponseJson(), new UsersResponseJson());

    private List users = Arrays.asList(new User(), new User());

    @Mock
    private User user;

    @Before
    public void before() {
        when(helper.convertUsersToJsonString(userResponseJson)).thenReturn("{}");
        when(helper.convertUsersToJsonUsers(users)).thenReturn(userResponseJson);
        when(userDao.searchUsersBySearchString("testSearchString")).thenReturn(users);
        when(userDao.findUserByUsername(username)).thenReturn(user);
        when(userDao.findUsersByLastname(lastname)).thenReturn(Arrays.asList(new User(), new User()));
        when(userDao.findUsersByFirstname(username)).thenReturn(Arrays.asList(new User(), new User()));
        when(userDao.findUserByUsername(username)).thenReturn(user);
        when(user.getPassword()).thenReturn(password);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByUsernameThrowsException() throws UserNotFoundException {
        userService.findUserByUsername("falseUsername");
    }

    @Test
    public void testSearchUsers() throws UserNotFoundException {
        List<User> usersByFirstname = userService.findUsersByFirstname(username);
        assertThat(usersByFirstname.size()).isEqualTo(2);
        verify(userDao, times(1)).findUsersByFirstname(username);

        List<User> usersByLastname = userService.findUsersByLastname(lastname);
        assertThat(usersByLastname.size()).isEqualTo(2);
        verify(userDao, atLeastOnce()).findUsersByLastname(lastname);

        User user = userService.findUserByUsername(username);
        assertThat(user).isNotNull();
        assertThat(user.getPassword()).isEqualTo(password);
        verify(userDao, atLeastOnce()).findUserByUsername(username);

        String jsonResult = userService.searchUsersBySearchString("testSearchString");
        assertThat(jsonResult).isEqualTo("{}");

        verify(userDao, atLeastOnce()).searchUsersBySearchString("testSearchString");
        verify(helper, times(1)).convertUsersToJsonUsers(users);
        verify(helper, times(1)).convertUsersToJsonString(userResponseJson);
    }

    @Test
    public void testPersistUser() {
        userService.persistUser(user);
        verify(userDao, times(1)).persistUser(user);
    }

    @Test
    public void testLoginUser() throws UserNotFoundException, PasswordNotMatchException {
        when(user.getPassword()).thenReturn(password);
        userService.loginUser(username, password);
    }

    @Test(expected = UserNotFoundException.class)
    public void testloginUserThrowsUserNotFoundException() throws UserNotFoundException, PasswordNotMatchException {
        userService.loginUser("testwrongusername", password);
    }

    @Test(expected = PasswordNotMatchException.class)
    public void testloginUserThrowsPasswordNotMatchException() throws UserNotFoundException, PasswordNotMatchException {
        userService.loginUser(username, "testwrongpassword");
    }

    @Test
    public void testUserServiceImpl() {
        UserServiceImpl userService = new UserServiceImpl();
    }
}
