package com.gng.network.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.gng.network.dao.PostDao;
import com.gng.network.enities.Comment;
import com.gng.network.enities.Post;
import com.gng.network.enities.PostLike;
import com.gng.network.enities.User;
import com.gng.network.enums.PostLikedStatus;
import com.gng.network.exceptions.CommentNotFoundException;
import com.gng.network.exceptions.NullObjectException;
import com.gng.network.exceptions.NullObjectIdException;
import com.gng.network.exceptions.NullPostIdException;
import com.gng.network.exceptions.NullResultException;
import com.gng.network.exceptions.NullUserIdException;
import com.gng.network.exceptions.PostNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.helper.UserHelper;
import com.gng.network.json.response.PostLikeJsonResponse;
import com.gng.network.service.PostService;
import com.gng.network.service.UserService;

@Service(value = "postService")
public class PostServiceImpl implements PostService {

	@Inject
	private PostDao postDao;
	
	@Inject
	private UserService userService;
	
	@Inject
	private UserHelper userHelper;
	
	@Inject
	private MessageSource messageSource;
	
	private static final Logger logger = LoggerFactory.getLogger(PostService.class);
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public void getFriendsActivePosts(Integer userId, ModelAndView mav) throws UserNotFoundException {
		User user = userService.findUserById(userId);
    	List<Integer> ids = userHelper.getUsersIds(user.getFriends());
    	ids.add(userId);
		List<Post> posts = postDao.getFriendPosts(ids);
		Map<Integer, PostLike> likes = new HashMap<Integer,  PostLike>(); 
		for(Post post: posts) {
			for(PostLike postLike : post.getLikes()) {
				if(postLike.getUser().getId() == userId) {
					likes.put(post.getId(), postLike);
				}
			}
		}
		mav.addObject("posts", posts);
		mav.addObject("postLikes", likes);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void addPost(Post post) throws UserNotFoundException {
		postDao.save(post);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public Post findPostById(Integer id) throws PostNotFoundException {
		Post post = postDao.findPostById(id);
		if(post == null) {
			throw new PostNotFoundException(messageSource.getMessage("post.not.found.exception.message", new Object [] {id}, null));
		}
		return post;
	}
	
	public void removePost(Integer postId) throws PostNotFoundException {
		if(postId ==  null) {
			throw new IllegalArgumentException("postId cannot not be null");
		}
		postDao.removePost(postId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer addComment(String username, Integer postId, String commentContent) throws PostNotFoundException, UserNotFoundException {
		Post post = findPostById(postId);
		User user = userService.findUserByUsername(username);
		Comment comment = new Comment();
		comment.setCommentContent(commentContent);
		comment.setDate(new Date());
		comment.setPost(post);
		comment.setUser(user);
		try {
			URL url = new URL("http", "localhost", 8080, "test");
			URLConnection urlConnection = url.openConnection();
			urlConnection.addRequestProperty("comment", comment.getCommentContent());
		} catch (MalformedURLException e) {
			logger.info("error while constructing URL object : " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return postDao.addComment(comment);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void removeComment(Integer commentId) throws CommentNotFoundException {
		if(commentId == null) throw new IllegalArgumentException("commentId cannot be null");
		postDao.removeComment(commentId);
	}

	public Comment findCommentById(Integer commentId) throws CommentNotFoundException {
		if(commentId ==  null) {
			throw new IllegalArgumentException("commentId must not be null");
		}
		return postDao.findCommentById(commentId);
	}

	@Override
	public PostLikeJsonResponse likePost(Integer userId, Integer postId) throws UserNotFoundException, PostNotFoundException, NullPostIdException, NoSuchMessageException, NullObjectException, NullUserIdException, NullResultException {
		try {
			if(userId == null || userId == 0) {
				throw new NullUserIdException("User Id cannot be null");
			}
			if(postId == null || postId == 0) {
				throw new NullPostIdException("Post Id cannot be null");
			}
		} catch(NullPostIdException ex) {
			logger.info(ex.getMessage());
		}
		try {
			PostLike postLike = postDao.findPostLikeByUserAndPostIds(userId, postId);
			if(postLike == null) {
				User user = userService.findUserById(userId);
				Post post = findPostById(postId);
				postLike = new PostLike(user, post);
				postDao.savePostLike(postLike);
				PostLikeJsonResponse jsonResponse = createPostLikeJsonResponse(postLike);
				return jsonResponse;
			} else {
				postLike.setLikeStatus(PostLikedStatus.LIKED);
				postDao.savePostLike(postLike);
				PostLikeJsonResponse jsonResponse = createPostLikeJsonResponse(postLike);
				return jsonResponse;
			}
		} catch (UserNotFoundException ex) {
			logger.info(ex.getMessage());
			throw new UserNotFoundException(ex.getMessage());
		} catch(PostNotFoundException ex) {
			logger.info(ex.getMessage());
			throw new PostNotFoundException(ex.getMessage());
		} catch (NullObjectException ex) {
			logger.info(ex.getMessage());
			throw new NullObjectException(ex.getMessage());
		} 
	}

	private PostLikeJsonResponse createPostLikeJsonResponse(PostLike postLike) {
		PostLikeJsonResponse jsonResponse = new PostLikeJsonResponse();
		jsonResponse.setPostLikeId(postLike.getId());
		jsonResponse.setUserId(postLike.getUser().getId());
		jsonResponse.setPostId(postLike.getPost().getId());
		jsonResponse.setPostLikeText(messageSource.getMessage("post.like.response.text.post.liked", null, null));
		jsonResponse.setNumberOfLikes(postLike.getPost().getLikes().size());
		return jsonResponse;
	}

	@Override
	public PostLikeJsonResponse unlikePost(Integer userId, Integer postId) throws UserNotFoundException, PostNotFoundException, NullPostIdException, NoSuchMessageException, NullUserIdException, NullObjectIdException, NullResultException {
		try {
			if(userId == null || userId == 0) {
				throw new NullUserIdException("User Id cannot be null");
			}
			if(postId == null || postId == 0) {
				throw new NullPostIdException("Post Id cannot be null");
			}
		} catch(NullPostIdException ex) {
			logger.info(ex.getMessage());
		}
		try {
			User user = userService.findUserById(userId);
			Post post = findPostById(postId);
			PostLike postLike = postDao.findPostLikeByUserAndPostIds(userId, postId);
			if(postLike == null) {
				throw new NullResultException(messageSource.getMessage("post.like.null.result.exception.message", new Object[] {userId, postId}, null));
			}
			postDao.removePostLike(postLike);
			PostLikeJsonResponse jsonResponse = createPostUnlikeJsonResponse(user, post, postLike);
			return jsonResponse;
		} catch (UserNotFoundException ex) {
			logger.info(ex.getMessage());
			throw new UserNotFoundException(ex.getMessage());
		} catch(PostNotFoundException ex) {
			logger.info(ex.getMessage());
			throw new PostNotFoundException(ex.getMessage());
		} catch (NullResultException ex) {
			logger.info(ex.getMessage());
			throw new NullResultException(ex.getMessage());
		} 
	}

	private PostLikeJsonResponse createPostUnlikeJsonResponse(User user, Post post, PostLike postLike) {
		PostLikeJsonResponse jsonResponse = new PostLikeJsonResponse();
		jsonResponse.setPostLikeId(postLike.getId());
		jsonResponse.setUserId(user.getId());
		jsonResponse.setPostId(post.getId());
		jsonResponse.setPostLikeText(messageSource.getMessage("post.like.response.text.post.unliked", null, null));
		return jsonResponse;
	}
	
}
