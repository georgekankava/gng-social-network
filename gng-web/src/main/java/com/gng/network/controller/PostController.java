package com.gng.network.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gng.network.enities.Post;
import com.gng.network.enities.PostLike;
import com.gng.network.enities.User;
import com.gng.network.exceptions.CommentNotFoundException;
import com.gng.network.exceptions.NullObjectException;
import com.gng.network.exceptions.NullObjectIdException;
import com.gng.network.exceptions.NullPostIdException;
import com.gng.network.exceptions.NullResultException;
import com.gng.network.exceptions.NullUserIdException;
import com.gng.network.exceptions.PostNotFoundException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.html.HTMLTag;
import com.gng.network.json.response.PostLikeJsonResponse;
import com.gng.network.json.response.PostResponseJson;
import com.gng.network.json.response.RemovePostJsonResponse;
import com.gng.network.service.PostService;
import com.gng.network.service.UserService;

@Controller
public class PostController {
    @Inject
    private UserService userService;
    
    @Inject
    private PostService postService;
    
    @Inject
    private MessageSource messageSource;
    
    private Pattern urlPattern = Pattern.compile("^(https://|https://www.|http://|http://www.|www.)(\\w*)\\.(.*)");
    
    Pattern htmlMetaDescriptionTagPattern = Pattern.compile("<meta\\s+name=\"description\"\\s+content=\".*\">");
    
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    
    @ResponseBody
    @RequestMapping(value = "/add-post.ajax", method=RequestMethod.GET, produces={"application/json"})
    public PostResponseJson addPost(@RequestParam String username, @RequestParam String postContent) {
        try {
            User user = userService.findUserByUsername(username);
            Post post = new Post();
            analyzePost(postContent, post);
            post.setImage(null);
            post.setPostDate(new Date());
            if(post.getUrl() == null) {
                post.setText(postContent);
            }
            post.setUser(user);
            postService.addPost(post);
            PostResponseJson postResponseJson = new PostResponseJson();
            postResponseJson.setPostId(post.getId());
            postResponseJson.setPostContent(post.getText() != null ? post.getText() : post.getUrl());
            postResponseJson.setDate(post.getPostDate().getTime());
            return postResponseJson;
        } catch (UserNotFoundException e) {
            logger.error("user with id [" + username + "] not found");
        }
        return null;
    }
    
    private void analyzePost(String postContent, Post post) {
        if(urlPattern.matcher(postContent).matches()) {
            if(postContent.contains(messageSource.getMessage("youtube.url",    null, null))) {
                String youtubeVideoURLId = messageSource.getMessage("youtube.embed.url", null, null) + postContent.substring(postContent.indexOf('=') + 1, postContent.length());
                post.setUrl(youtubeVideoURLId);
            } else {
                post.setUrl(postContent);
            }
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/remove-post.ajax", method=RequestMethod.GET, produces={"application/json"})
    public RemovePostJsonResponse removePost(@RequestParam Integer postId) {
        RemovePostJsonResponse removePostJsonResponse = new RemovePostJsonResponse();
        try {
            postService.removePost(postId);
            removePostJsonResponse.setPostRemoveResponse(messageSource.getMessage("post.remove.response.json", null, null));
        } catch (PostNotFoundException e) {
            e.printStackTrace();
            removePostJsonResponse.setPostRemoveResponse(messageSource.getMessage("post.remove.error.response.json", null, null));
            logger.info("post with id [" + postId+ "] not found");
        }
        return removePostJsonResponse;
    }
    
    @ResponseBody
    @RequestMapping(value = "/like-unlike-post.ajax", method=RequestMethod.GET, produces={"application/json"})
    public PostLikeJsonResponse likeOrUnlikePost(@RequestParam Integer userId, @RequestParam Integer postId, @RequestParam String likeAction) throws NoSuchMessageException, UserNotFoundException, PostNotFoundException, NullPostIdException, NullObjectException {
        try {
            PostLikeJsonResponse jsonResponse = null;
            if(likeAction.equalsIgnoreCase(messageSource.getMessage("post.text.like", null, null))) {
                jsonResponse = postService.likePost(userId, postId);
            } else if(likeAction.equalsIgnoreCase(messageSource.getMessage("post.text.unlike", null, null))) {
                jsonResponse = postService.unlikePost(userId, postId);
            }
            return jsonResponse;
        } catch (NullUserIdException e) {
            logger.info(e.getMessage());
        } catch (NullResultException e) {
            logger.info(e.getMessage());
        } catch (NullObjectIdException e) {
            logger.info(e.getMessage());
        }
        // empty response
        return new PostLikeJsonResponse();
    }
    
    /**
     * @param postUrl
     * @return
     */
    /**
     * @param postUrl
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/read-post-url.ajax", method=RequestMethod.GET, produces={"application/json"})
    public List<HTMLTag> readPostUrl(@RequestParam String postUrl) {
    	List<HTMLTag> htmlTags = new ArrayList<HTMLTag>();
    	try {
	    	if(urlPattern.matcher(postUrl).matches()) {
	    		
	    			URL url = new URL(postUrl);
	    	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	    	        String inputLine;
	    	        List<String> content = new ArrayList<String>();
	    	        StringBuilder pageContent = new StringBuilder();
	    	        String hostUrl = url.getProtocol() + "://" + url.getHost() + ":" + (url.getDefaultPort() != -1 ? url.getDefaultPort() : "");
	    	        while ((inputLine = in.readLine()) != null) {
	    	        	pageContent.append(inputLine);
	    	            content.add(inputLine);
	    	        }
	    	        in.close();
	    	        String pageMetaDescription = "";
	    	        String htmlMetaOpenerTag = messageSource.getMessage("post.url.meta.tag", null, "<meta", null);
	    	        String htmlMetaTagNameAttribute = messageSource.getMessage("post.url.meta.tag.name.attribute", null, "name=\"description\"", null);
	    	        for(String contentLine : content) {
	    	        	contentLine = contentLine.toLowerCase();
	    	        	if(contentLine.indexOf(htmlMetaOpenerTag.toLowerCase()) != -1) {
	    	        		if(contentLine.indexOf(htmlMetaTagNameAttribute.toLowerCase()) != -1) {
	    	        			String metaTagContentAttribute = messageSource.getMessage("post.url.meta.tag.content.attribute", null, "content=\"", null).toLowerCase();
	    	        			int contentAttributeStartIndex = contentLine.indexOf(metaTagContentAttribute);
	    	        			if(contentAttributeStartIndex != -1) {
	    	        				contentAttributeStartIndex += metaTagContentAttribute.length();
	    	        				int contentAttributeEndindex = contentLine.indexOf("\"", contentAttributeStartIndex);
	    	        				if(contentAttributeEndindex != -1) {
		    	        				pageMetaDescription = contentLine.substring(contentAttributeStartIndex, contentAttributeEndindex);
		    	        				if(!pageMetaDescription.trim().equals("")) {
			    	        				HTMLTag htmlTag = new HTMLTag();
			        	        			htmlTag.setTagName("meta");
			        	        			htmlTag.addAttribute("content", pageMetaDescription);
			        	        			htmlTags.add(htmlTag);
		    	        				}
	    	        				}
	    	        			}
	    	        		}
	    	        	} if(contentLine.indexOf("<img") != -1) {
	    	        		String srcAttributeNameWithDoubleQuote = messageSource.getMessage("html.img.tag.src.attribute.name.with.double.quote", null, " src=\"", null);
	    	        		String srcAttributeNameWithSingleQuote = messageSource.getMessage("html.img.tag.src.attribute.name.with.single.quote", null, " src='", null);
	    	        		String srcAttributeName = messageSource.getMessage("html.img.tag.src.attribute.name.with.single.quote", null, "src", null);
	    	        		int srcAttributeStartIndex = contentLine.indexOf(srcAttributeNameWithDoubleQuote);
	    	        		if(srcAttributeStartIndex == -1) {
	    	        			srcAttributeStartIndex = contentLine.indexOf(srcAttributeNameWithSingleQuote);
	    	        		}
	    	        		// add length of the search string
	    	        		srcAttributeStartIndex += srcAttributeNameWithDoubleQuote.length();
	    	        		int srcAttributeEndIndex = contentLine.indexOf("\"", srcAttributeStartIndex);
	    	        		// in case it starts with '
	    	        		if(srcAttributeEndIndex == -1) {
	    	        			// adding =" or ='
	    	        			srcAttributeEndIndex = contentLine.indexOf("\'", srcAttributeStartIndex);
	    	        		}
	    	        		if(srcAttributeStartIndex != -1) {
	    	        			HTMLTag htmlTag = new HTMLTag();
	    	        			htmlTag.setTagName("img");
	    	        			String srcAttributeValue = contentLine.substring(srcAttributeStartIndex, srcAttributeEndIndex);
	    	        			htmlTag.addAttribute(srcAttributeName, hostUrl + srcAttributeValue);
	    	        			htmlTags.add(htmlTag);
	    	        		}
	    	        	}
	    	        }
	    	}
    	} catch (MalformedURLException e) {
    		logger.info(e.getMessage());
    	} catch (IOException e) {
    		logger.info(e.getMessage());
    	}
    	return htmlTags;
    }
    
    @RequestMapping(value = "/show-post-like-friends-list.ajax")
    public ModelAndView showPostLikeFriendsList(@RequestParam Integer userId, @RequestParam Integer postId) throws NullPostIdException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("post-like-friends-list");
        try {
            if(postId == null) {
                throw new NullPostIdException("method showPostLikeFriendsList(Integer) with null postId");
            }
            Post post = postService.findPostById(postId);
            List<User> postLikeFriendList = new ArrayList<User>();
            for(PostLike postLike : post.getLikes()) {
                User user = postLike.getUser();
                if(user.getId() != userId) {
                    postLikeFriendList.add(user);
                }
            }
            mav.addObject("postLikeFriendList", postLikeFriendList);
            return mav;
        } catch(NullPostIdException e) {
            logger.info(e.getMessage());
            mav.setViewName("general-ajax-error-message");
        } catch (PostNotFoundException e) {
            logger.info(e.getMessage());
            mav.setViewName("general-ajax-error-message");
        }
        return mav;
        
    }
    
    @ResponseBody
    @RequestMapping(value = "/add-comment.ajax", method=RequestMethod.GET)
    public Integer addComment(HttpServletResponse response, @RequestParam String username, 
                              @RequestParam Integer postId, @RequestParam String commentContent) {
        try {
            return postService.addComment(username, postId, commentContent);
        } catch (PostNotFoundException e) {
            logger.error("post with id [" + postId + "] was not found");
        } catch (UserNotFoundException e) {
            logger.error("user with username [" + username + "] was not found");
        }
        return null;
        
    }
    
    @ResponseBody
    @RequestMapping(value = "/remove-comment.ajax", method=RequestMethod.GET)
    public void removePostComment(@RequestParam Integer commentId) {
        try {
            postService.removeComment(commentId);
        } catch (CommentNotFoundException e) {
            logger.error("comment with id [" + commentId + "] was not found");
        } 
        
    }
    
}
