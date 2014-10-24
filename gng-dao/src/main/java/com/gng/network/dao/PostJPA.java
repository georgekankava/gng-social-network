package com.gng.network.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.constants.WebConstants;
import com.gng.network.enities.Comment;
import com.gng.network.enities.Post;
import com.gng.network.enities.PostLike;
import com.gng.network.exceptions.CommentNotFoundException;
import com.gng.network.exceptions.NullObjectException;
import com.gng.network.exceptions.NullObjectIdException;
import com.gng.network.exceptions.PostNotFoundException;

@Repository(value="postDao")
public class PostJPA implements PostDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private MessageSource messageSource;
	
	@SuppressWarnings("unchecked")
	public List<Post> getFriendPosts(List<Integer> ids) {
		if(!ids.isEmpty()) {
			return em.createNamedQuery("Post.getFriendsActivePosts")
					.setParameter("ids", ids).setParameter("postFromDate", new Date(WebConstants.POST_FROM_DATE)).setParameter("postToDate", new Date())
					.getResultList();
		}
		return null;
	}

	public void save(Post post) {
		if(post.getId() == null) {
			em.persist(post);
		} else {
			em.merge(post);
		}
	}
	
	@Transactional
	public void removePost(Integer postId) throws PostNotFoundException {
		Post post = em.find(Post.class, postId);
		if(post == null) {
			throw new PostNotFoundException(messageSource.getMessage("post.not.found.exception.message", new Object [] {postId}, null));
		}
		em.remove(post);
	}

	public Post findPostById(Integer id) {
		Post post = em.find(Post.class, id);
		return post;
	}

	public Integer addComment(Comment comment) {
		em.persist(comment);
		em.refresh(comment);
		return comment.getId();
	}

	public void removeComment(Integer commentId) throws CommentNotFoundException {
		Comment comment = em.find(Comment.class, commentId);
		em.remove(comment);
	}

	public Comment findCommentById(Integer commentId) throws CommentNotFoundException {
		return em.find(Comment.class, commentId);
	}

	@Transactional
	@Override
	public void savePostLike(PostLike postLike) throws NoSuchMessageException, NullObjectException {
		if(postLike == null) {
			throw new NullObjectException(messageSource.getMessage("null.object.exception.postlike", null, null));
		}
		if(postLike.getPost() == null) {
			throw new NullObjectException(messageSource.getMessage("null.object.exception.post", null, null));
		}
		if(postLike.getUser() == null) {
			throw new NullObjectException(messageSource.getMessage("null.object.exception.user", null, null));
		}
		if(postLike.getId() == null) {
			em.persist(postLike);
		} else {
			em.merge(postLike);
		}
	}
	
	@Override
	public PostLike findPostLikeByUserAndPostIds(Integer userId, Integer postId) {
		try {
			TypedQuery<PostLike> plQuery = em.createNamedQuery("PostLike.findPostLikeByUserAndPostIds", PostLike.class);
			PostLike pl = plQuery.setParameter("userId", userId).setParameter("postId", postId).getSingleResult();
			return pl;
		}catch(NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	@Override
	public void removePostLike(PostLike postLike) throws NoSuchMessageException, NullObjectIdException {
		if(postLike.getId() == null) {
			throw new NullObjectIdException(messageSource.getMessage("post.like.with.null.id.exception.message", null, null));
		}
		PostLike postLikeMerged = em.merge(postLike);
		em.remove(postLikeMerged);
	}

}
