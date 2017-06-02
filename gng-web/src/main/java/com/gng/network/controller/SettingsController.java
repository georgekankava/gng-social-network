package com.gng.network.controller;

import com.gng.network.enums.UserPrivacyEnum;
import com.gng.network.exceptions.PasswordDoNotMatchException;
import com.gng.network.exceptions.ServiceException;
import com.gng.network.security.FormUser;
import com.gng.network.security.UserContext;
import com.gng.network.service.SettingsService;
import com.gng.network.utils.ApplicationUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * Created by georgekankava on 24.03.17.
 */
@Controller
@Slf4j
public class SettingsController {

    @Inject
    private SettingsService settingsService;

    @Inject
    private MessageSource messageSource;

    @RequestMapping("/settings")
    public String loadSecurityPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "settings";
    }

    @RequestMapping("/privacy")
    public String privacy() {
        return "inc/privacy";
    }

    @ResponseBody
    @RequestMapping(value = "/user-search-participation", method = RequestMethod.GET)
    public UserSearchParticipation getUserSearchParticipationPrivacy() {
        String username = UserContext.getLoggedUser().getUsername();
        boolean participatesInSearch = settingsService.getUserParticipatesInNetworkSearch(username);
        return new UserSearchParticipation(participatesInSearch);
    }

    @ResponseBody
    @RequestMapping(value = "/user-add-as-friend-strategy", method = RequestMethod.GET)
    public UserPrivacyEnum getUserAddAsFriendPrivacy() {
        String username = UserContext.getLoggedUser().getUsername();
        return settingsService.getUserAddAsFriendPrivacy(username);
    }

    @ResponseBody
    @RequestMapping("/participate-in-search.ajax")
    public SettingsResponseJson participateInSearchAjax(@RequestParam boolean participateInSearch) {
        FormUser loggedUser = UserContext.getLoggedUser();
        settingsService.participateInGNGNetworkSearch(loggedUser.getUsername(), participateInSearch);
        String responseMessage = messageSource.getMessage("user.privacy.page.participate.in.search.success.message", null, null);
        return new SettingsResponseJson(responseMessage, false);
    }

    @RequestMapping("/change-password.ajax")
    public String changePasswordAjax() {
        return "inc/change-password";
    }

    @RequestMapping(value = "view-friends-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object modifyViewFriendsListStrategy(@RequestParam("viewFriendsList") UserPrivacyEnum userPrivacyEnum) {
        try {
            String username = UserContext.getLoggedUser().getUsername();
            settingsService.updateUsersFriendsListViewStrategy(username, userPrivacyEnum);
        } catch (Exception e) {

        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/process-change-password.ajax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SettingsResponseJson processChangePassword(@Valid UserPassword userPassword, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError("newPassword");
                String messageKey = fieldError.getDefaultMessage();
                String successMessage = messageSource.getMessage(messageKey, null, null);
                return new SettingsResponseJson(successMessage, true);
            }
            if(!ApplicationUtils.validatePasswordMatches(userPassword.getNewPassword(), userPassword.getConfirmNewPassword())) {
                throw new PasswordDoNotMatchException("New Password And Confirm New Password do not match", "reset.password.page.passwords.not.match.message");
            }
            settingsService.validateAndChangeUserPassword(userPassword.getUserId(), userPassword.getCurrentPassword(), userPassword.getNewPassword());
            String successMessage = messageSource.getMessage("password.change.page.password.successfully.changed.message", null, null);
            return new SettingsResponseJson(successMessage, false);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            String errorMessage = messageSource.getMessage(e.getKeyCode(), null, null);
            return new SettingsResponseJson(errorMessage, true);
        } catch (PasswordDoNotMatchException e) {
            log.error(e.getMessage(), e);
            String errorMessage = messageSource.getMessage(e.getKeyCode(), null, null);
            return new SettingsResponseJson(errorMessage, true);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user-lookup-strategy", method = RequestMethod.POST)
    public SettingsResponseJson changeUserContactPrivacy(@RequestParam("userPrivacyEnum") UserPrivacyEnum userPrivacyEnum) {
        try {
            FormUser loggedUser = UserContext.getLoggedUser();
            settingsService.updateUserAddAsFriendPrivacy(loggedUser.getUsername(), userPrivacyEnum);
            String successMessage = messageSource.getMessage("setting.successfully.changed.message", null, null);
            return new SettingsResponseJson(successMessage, false);
        } catch (Exception e) {
            String errorMessage = messageSource.getMessage("general.server.error.message", null, null);
            return new SettingsResponseJson(errorMessage, true);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserPassword {
        private Integer userId;
        private String currentPassword;
        @Pattern(regexp = "^([a-zA-Z0-9]{8,15})$", message = "change.password.pattern.failed.message")
        private String newPassword;
        private String confirmNewPassword;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class SettingsResponseJson {
        private String message;
        private boolean errorMessage;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class UserSearchParticipation {
        private boolean participatesInSearch;
    }

}
