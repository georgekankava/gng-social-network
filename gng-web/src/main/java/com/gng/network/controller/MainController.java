package com.gng.network.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gng.network.enities.Event;
import com.gng.network.enities.FriendRequest;
import com.gng.network.enities.Image;
import com.gng.network.enities.User;
import com.gng.network.exceptions.ImageUploadException;
import com.gng.network.exceptions.NullUserIdException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.forms.UserSignupData;
import com.gng.network.helper.UserHelper;
import com.gng.network.security.FormUser;
import com.gng.network.security.UserContext;
import com.gng.network.service.EventService;
import com.gng.network.service.FriendRequestService;
import com.gng.network.service.PostService;
import com.gng.network.service.UserService;

@Controller
public class MainController {

	@Inject
    private UserService userService;
	
	@Inject
    private PostService postService;
	
	@Inject
	private FriendRequestService friendRequestService;
	
	@Inject
    private EventService eventService;
    
    @Inject
    private MessageSource messageSource;
    
    @Inject
    private UserHelper userHelper;
    
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    
	private Pattern pattern = Pattern.compile("([a-zA-Z0-9._-]*[\\s]*)*");
	
	
    /**
     * Selects the home page and populates the model with a message
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(ModelAndView mav) {
    	try {
    		FormUser formUser = UserContext.getLoggedUser();
    		Integer userId = formUser.getUserId();
    		postService.getFriendsActivePosts(userId, mav);
			User user = userService.findUserById(userId);
			List<FriendRequest> friendRequests = friendRequestService.getPendingRequests(userId);
			mav.addObject("friends", userService.updateUsersOnlineStatuses(user.getFriends()));
			List<Event> events = eventService.getActiveEvents(userId);
			mav.addObject("friendRequests", friendRequests);
			mav.addObject("events", events);
			mav.setViewName("home");
		} catch (UserNotFoundException e) {
			mav.setViewName("home");
			logger.info("User not found: " + e.getMessage());
		} catch (NullUserIdException e) {
			mav.setViewName("home");
			logger.info("User Id cannot be null: " + e.getMessage());
		}          
        return mav;
    }
    
    @RequestMapping(value = "/profile")
	public ModelAndView profile(@RequestParam Integer userId, 
								ModelAndView mav, 
								HttpSession session) {
		
		try {
			Integer loggedUserId = UserContext.getLoggedUser().getUserId();
			User loggedUser = userService.findUserById(loggedUserId);
			User user = userService.findUserById(userId);
			List<Image> images = userService.getUserImages(userId);
			List<User> friends = userService.getUsersFriends(user);
			Boolean isFriend = userHelper.isFriend(loggedUser, user);
			mav.addObject("loggedUser", loggedUser);
			mav.addObject("user", user);
			mav.addObject("friends", friends);
			mav.addObject("images", images);
			mav.addObject("isFriend", isFriend);
			mav.setViewName("profile");
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			mav.setViewName("profile-not-found");
			return mav;
		}
		return mav;
	}
    
    @ResponseBody
	@RequestMapping(value = "/search-people.ajax", method=RequestMethod.GET)
    public String searchPeople(@RequestParam String searchString) {
    	if(pattern.matcher(searchString).matches()) {
    		String responserJson =  userService.searchUsersBySearchString(searchString);
        	return responserJson;
    	}
    	String responseMessage = messageSource.getMessage("user.search.regex.invalid", null, null);
    	return responseMessage;
    }
	
	@RequestMapping(value = "/register-user.html", method = RequestMethod.POST)
	public ModelAndView registerUser(HttpServletRequest httpServletRequest, 
									 HttpServletResponse httpServletResponse, 
									 ModelAndView mav,
									 @Valid @ModelAttribute("userSignupData") UserSignupData userSignupData, 
									 BindingResult result,
									 @RequestParam(value="image", required = false) MultipartFile image) throws IOException, NoSuchAlgorithmException {
		if(result.hasErrors()) {
			mav.setViewName("welcome");
			return mav;
		}
		try {
			User user  = userHelper.userSignupDataToUser(userSignupData);
			userService.persistUser(user);
			if(!image.isEmpty()) {
				validateImage(image);
				saveImageIntoDB(image.getOriginalFilename(), image, user, true);
			}
		} catch(ImageUploadException ex) {
			result.reject(ex.getMessage());
			mav.setViewName("welcome");
			return mav;
		}
		mav.setViewName("welcome");
		return mav;
	}
	
	private void validateImage(MultipartFile image) throws ImageUploadException {
		if(!image.getContentType().matches("image/.*")) {
			throw new ImageUploadException("Only image files accepted");
		}
	}
	
	
	@SuppressWarnings("unused")
	private String saveImage(String filename, 
							 MultipartFile image) throws ImageUploadException {
		String storagePath = messageSource.getMessage("profile.image.location", null, Locale.getDefault());
		try {
			String originalFileName = image.getOriginalFilename();
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
			File file = new File(storagePath + File.separatorChar + filename + fileExtension);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			return fileExtension;
		} catch(IOException ex) {
			throw new ImageUploadException("Unable to save image", ex);
		}
	}
	
	private void saveImageIntoDB(String filename, MultipartFile image, User user, boolean profileImage) throws ImageUploadException {
		try {
			String originalFileName = image.getOriginalFilename();
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
			Image img = new Image();
			img.setAlbum(null);
			img.setDate(new Date());
			img.setDescription("Profile Image");
			img.setExtention(fileExtension);
			img.setImage(image.getBytes());
			img.setName(filename);
			img.setProfileImage(profileImage);
			img.setUser(user);
			userService.addImage(img);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		
	}
	
	@RequestMapping("/photos.html")
	public ModelAndView photos(@RequestParam Integer userId, HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		getUserImages(userId, mav);
		mav.addObject("userId", userId);
		mav.setViewName("photos");
		return mav;
	}
	
	@RequestMapping("/add-photo.html")
	public ModelAndView addPhoto(@RequestParam Integer userId, HttpServletRequest request, HttpServletResponse response, ModelAndView mav, @RequestParam(value="image", required = false) MultipartFile image) throws UserNotFoundException, ImageUploadException {
		if(!image.isEmpty()) {
			validateImage(image);
			User user = userService.findUserById(userId);
			saveImageIntoDB(image.getOriginalFilename(), image, user, false);
		}
		getUserImages(userId, mav);
		mav.setViewName("photos");
		return mav;
	}

	private void getUserImages(Integer userId, ModelAndView mav) {
		List<Image> images = userService.getUserImages(userId);
		mav.addObject("images", images);
	}
	
}
