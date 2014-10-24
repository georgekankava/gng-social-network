package com.gng.network.dao;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.gng.network.enities.Comment;
import com.gng.network.enities.Post;
import com.gng.network.enities.PostLike;
import com.gng.network.exceptions.CommentNotFoundException;
import com.gng.network.exceptions.NullObjectException;
import com.gng.network.exceptions.NullObjectIdException;
import com.gng.network.exceptions.PostNotFoundException;

public interface PostDao {
	List<Post> getFriendPosts(List<Integer> ids);
	void save(Post post);
	Integer addComment(Comment comment);
	void removePost(Integer postId) throws PostNotFoundException;
	Comment findCommentById(Integer commentId) throws CommentNotFoundException;
	Post findPostById(Integer id);
	void removeComment(Integer commentId) throws CommentNotFoundException;
	void savePostLike(PostLike postLike) throws NoSuchMessageException, NullObjectException;
	PostLike findPostLikeByUserAndPostIds(Integer userId, Integer postId);
	void removePostLike(PostLike postLike) throws NoSuchMessageException, NullObjectIdException;
}
