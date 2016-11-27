package com.gng.network.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gng.network.exceptions.PostNotFoundException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

import com.gng.network.dao.PostDao;
import com.gng.network.enities.Post;
import com.gng.network.enities.PostLike;
import com.gng.network.enities.User;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.service.impl.PostServiceImpl;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

	@Mock
	private PostDao postDao;
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserHelper userHelper;
	
	@Mock
	private MessageSource messageSource;
	
	@InjectMocks
	PostServiceImpl postServiceImpl = new PostServiceImpl();

	private static User user1;
	
	private static User user2;
	
	private static User user3;
	
	@BeforeClass
	public static void setup() {
		user1 = mock(User.class);
		user2 = mock(User.class);
		user3 = mock(User.class);
        when(user1.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(2);
        when(user3.getId()).thenReturn(3);
	}
	
	@Test
	public final void testGetFriendsActivePosts() throws UserNotFoundException {
		User user = mock(User.class);
		Post post1 = mock(Post.class);
		Post post2 = mock(Post.class);
		PostLike postLike1 = mock(PostLike.class);
		PostLike postLike2 = mock(PostLike.class);
		PostLike postLike3 = mock(PostLike.class);
		PostLike postLike4 = mock(PostLike.class);
		when(postLike1.getUser()).thenReturn(user1);
		when(postLike2.getUser()).thenReturn(user2);
		when(postLike3.getUser()).thenReturn(user1);
		when(postLike4.getUser()).thenReturn(user2);
		when(userService.findUserById(1)).thenReturn(user);
        when(user.getFriends()).thenReturn(Arrays.asList(user1, user2, user3));
        List<Integer> friendsIds = new ArrayList<Integer>();
        friendsIds.addAll(Arrays.asList(1, 2, 3));
        when(userHelper.getUsersIds(user.getFriends())).thenReturn(friendsIds);
        when(postDao.getFriendPosts(userHelper.getUsersIds(user.getFriends()))).thenReturn(Arrays.asList(post1, post2));
        when(post1.getLikes()).thenReturn(Arrays.asList(postLike1, postLike2));
        when(post2.getLikes()).thenReturn(Arrays.asList(postLike3, postLike4));
        postServiceImpl.getFriendsActivePosts(1, mock(ModelAndView.class));
        
	}

	@Test
	public final void testAddPost() throws UserNotFoundException {
        postServiceImpl.addPost(new Post());
    }

	@Test(expected = PostNotFoundException.class)
	public final void testFindPostById() throws PostNotFoundException {
	    Post post = mock(Post.class);
	    when(postDao.findPostById(1)).thenReturn(post);
	    postServiceImpl.findPostById(1);
        postServiceImpl.findPostById(2);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testRemovePost() throws PostNotFoundException {
		postServiceImpl.removePost(1);
		postServiceImpl.removePost(null);
	}

	@Test
	public final void testAddComment() throws PostNotFoundException, UserNotFoundException {
		Post post = mock(Post.class);
		when(postDao.findPostById(1)).thenReturn(post);
		when(userService.findUserByUsername("username")).thenReturn(user1);

		postServiceImpl.addComment("username", 1, "test");

		verify(postDao).findPostById(1);
		verify(userService).findUserByUsername("username");
	}

	@Test
	public final void testRemoveComment() {

	}

	@Test
	public final void testFindCommentById() {
	}

	@Test
	public final void testLikePost() {
	}

	@Test
	public final void testUnlikePost() {
	}

}
