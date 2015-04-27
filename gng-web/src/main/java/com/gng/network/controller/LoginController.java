package com.gng.network.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gng.network.constants.WebConstants;
import com.gng.network.enities.User;
import com.gng.network.exceptions.PasswordNotMatchException;
import com.gng.network.exceptions.UserNotFoundException;
import com.gng.network.forms.LoginForm;
import com.gng.network.forms.UserSignupData;
import com.gng.network.service.UserService;

@Controller
public class LoginController {
    
    @Inject
    UserService userService;
    
    @Inject
    MessageSource messageSource;
    
    @RequestMapping("/login.html")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, @ModelAttribute("loginForm") LoginForm loginForm,
            @ModelAttribute("userSignupData") UserSignupData userSignupData,
            BindingResult bindingResult) {
            ModelAndView mav = new ModelAndView("home");
            User user = null;
            try {
            	user = userService.loginUser(loginForm.getUsername(), loginForm.getPassword());
            } catch(UserNotFoundException ex) {
                mav.setViewName("welcome");
                mav.addObject("errorMessage", true);
                return mav;
            } catch(PasswordNotMatchException ex) {
                mav.setViewName("welcome");
                mav.addObject("errorMessage", true);
                return mav;
            }
            session.setAttribute(WebConstants.USER, user);
            mav.addObject("user", user);
            return mav;
        
    }
}
