package com.gng.network.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.atmosphere.cpr.AtmosphereResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;

import com.gng.network.dao.UserDao;
import com.gng.network.enities.Image;
import com.gng.network.enities.User;
import com.gng.network.exceptions.PasswordNotMatchException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.json.response.UsersResponseJson;
import com.gng.network.service.impl.UserServiceImpl;
import com.gng.network.singletones.AtmosphereConnectionUuids;

/**
 * Created by georgekankava on 11/22/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private String username = "testusername";
    private String password = "testpassword";
    private String lastname = "testLastName";
    private String fullname = "Test Test";

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
        User userById = mock(User.class);
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        User user3 = mock(User.class);
        when(user1.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(2);
        when(user3.getId()).thenReturn(3);
        when(user1.getUsername()).thenReturn("user1");
        when(user2.getUsername()).thenReturn("user2");
        when(user3.getUsername()).thenReturn("user3");
        when(user.getFriends()).thenReturn(Arrays.asList(user1, user2, user3));
        when(userDao.mergeUser(user)).thenReturn(user);
        when(userById.getId()).thenReturn(23);
        when(userById.getFullname()).thenReturn(fullname);
        when(userDao.findUserById(23)).thenReturn(userById);
        when(userDao.findUserByFullname(fullname)).thenReturn(users);
        when(helper.convertUsersToJsonString(userResponseJson)).thenReturn("{}");
        when(helper.convertUsersToJsonUsers(users)).thenReturn(userResponseJson);
        when(userDao.searchUsersBySearchString("testSearchString")).thenReturn(users);
        when(userDao.findUserByUsername(username)).thenReturn(user);
        when(userDao.findUsersByLastname(lastname)).thenReturn(users);
        when(userDao.findUsersByFirstname(username)).thenReturn(users);
        when(userDao.findUserByUsername(username)).thenReturn(user);
        when(user.getPassword()).thenReturn(password);
    }

    @Test
    public void testFindUserImageByImageId() {
        when(userDao.findUserImageByImageId(anyInt())).thenReturn(new Image());
        Image image = userService.findUserImageByImageId(anyInt());
        assertThat(image).isNotNull();
    }

    @Test
    public void testGetUserImages() {
        when(userDao.getUserImagesById(anyInt())).thenReturn(Arrays.asList(new Image(), new Image()));
        List<Image> userImages = userService.getUserImages(anyInt());
        assertThat(userImages.size()).isEqualTo(2);
    }

    @Test
    public void testFindUserProfileImage() {
        when(userDao.findUserProfileImage(anyInt())).thenReturn(new Image());
        Image image = userService.findUserProfileImage(anyInt());
        assertThat(image).isNotNull();
    }

    @Test
    public void testPersistImage() {
        userService.addImage(new Image());
    }

    @Test
    public void testUpdateUsersOnlineStatuses() {
//        AtmosphereConnectionUuids atmosphereConnectionUuids = mock(AtmosphereConnectionUuids.class);
//        AtmosphereResource atmosphereResource1 = mock(AtmosphereResource.class);
//        when(atmosphereResource1.isCancelled()).thenReturn(true);
//        when(atmosphereConnectionUuids.getResource(1)).thenReturn(atmosphereResource1);
//        AtmosphereResource atmosphereResource2 = mock(AtmosphereResource.class);
//        when(atmosphereResource2.isCancelled()).thenReturn(false);
//        when(atmosphereConnectionUuids.getResource(2)).thenReturn(atmosphereResource2);
        User user1 = mock(User.class);
        User user2 = mock(User.class);
        when(user1.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(2);
        List<User> updatedUsers = userService.updateUsersOnlineStatuses(Arrays.asList(user1, user2));
        assertThat(updatedUsers.get(0).isOnline()).isEqualTo(false);
        assertThat(updatedUsers.get(1).isOnline()).isEqualTo(false);
    }
    
    @Test
    public void testGetUsersFriends() {
        List<User> userFriends = userService.getUsersFriends(user);
        assertThat(userFriends.size()).isEqualTo(3);
        assertThat(userFriends.get(0).getUsername()).isEqualTo("user1");
        assertThat(userFriends.get(1).getUsername()).isEqualTo("user2");
        assertThat(userFriends.get(2).getUsername()).isEqualTo("user3");
        verify(userDao, atLeastOnce()).mergeUser(user);
    }

    @Test
    public void testGetUsersFriendIds() {
        List<Integer> userFriendsIds = userService.getUsersFriendUserIds(user);
        assertThat(userFriendsIds.size()).isEqualTo(3);
        assertThat(userFriendsIds.get(0)).isEqualTo(1);
        assertThat(userFriendsIds.get(1)).isEqualTo(2);
        assertThat(userFriendsIds.get(2)).isEqualTo(3);
        verify(userDao, times(1)).mergeUser(user);
    }

    @Test
    public void testGetUsersFriendUsernames() {
        List<String> userFriendsUsernames = userService.getUsersFriendUsernames(user);
        assertThat(userFriendsUsernames.size()).isEqualTo(3);
        assertThat(userFriendsUsernames.get(0)).isEqualTo("user1");
        assertThat(userFriendsUsernames.get(1)).isEqualTo("user2");
        assertThat(userFriendsUsernames.get(2)).isEqualTo("user3");
        verify(userDao, times(1)).mergeUser(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void testFindUserByUsernameThrowsException() throws UserNotFoundException {
        User userByUsername = userService.findUserByUsername("falseUsername");
        assertThat(userByUsername).isNull();
        verify(userDao, times(1)).findUserByUsername("falseUsername");
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

        List<User> usersByFullname = userService.findUserByFullname(fullname);
        assertThat(usersByFullname).isNotEmpty();
        verify(userDao, times(1)).findUserByFullname(fullname);

        User userById = userService.findUserById(23);
        assertThat(userById).isNotNull();
        assertThat(userById.getId()).isEqualTo(23);
        assertThat(userById.getFullname()).isEqualTo(fullname);
        verify(userDao, atLeast(1)).findUserById(23);

        try {
            userService.findUserById(24);
        } catch(UserNotFoundException e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }

        userService.updateUser(this.user);
        verify(userDao, times(1)).updateUser(this.user);
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
