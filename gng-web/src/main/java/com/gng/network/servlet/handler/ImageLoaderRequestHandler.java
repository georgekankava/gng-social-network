package com.gng.network.servlet.handler;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import com.gng.network.enities.Image;
import com.gng.network.service.UserService;

@Component("imageLoaderRequestHandler")
public class ImageLoaderRequestHandler implements HttpRequestHandler {

	@Inject
    UserService userService;
	
	@Inject
    MessageSource messageSource;
    
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ImageLoaderRequestHandler.class);
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String pImage = request.getParameter("profileImage");
			boolean profileImage = Boolean.parseBoolean(pImage);
			if(profileImage) {
				Integer userId = Integer.parseInt(request.getParameter("id"));
				Image image = userService.findUserProfileImage(userId);
				//	String storagePath = messageSource.getMessage("profile.image.location", null, Locale.getDefault());
				//	String imagePath = storagePath + File.separatorChar + user.getProfileImage();
				//	File file = new File(imagePath);
				//	InputStream is = new BufferedInputStream(new FileInputStream(file));
				//	byte [] imageContent = new byte[is.available()];
				//	is.read(imageContent);
				//	is.close();
				String imageExtension = image.getExtention().substring(image.getExtention().lastIndexOf('.'));
				response.setContentLength(image.getImage().length);
				response.setContentType("image/" + imageExtension);
				//	byte [] bytes = new byte[image.getImage().length];
				//	for (int i = 0; i < image.getImage().length; i++) {
				//		bytes[i] = image.getImage()[i].byteValue();
				//	}
				response.getOutputStream().write(image.getImage());
			} else {
				Integer imageId = Integer.parseInt(request.getParameter("imageId"));
				Image image = userService.findUserImageByImageId(imageId);
				String imageExtension = image.getExtention().substring(image.getExtention().lastIndexOf('.'));
				response.setContentLength(image.getImage().length);
				response.setContentType("image/" + imageExtension);
				response.getOutputStream().write(image.getImage());
			}
		
	}

}
