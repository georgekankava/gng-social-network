package com.gng.network.controller;

import com.gng.network.exceptions.PasswordDoNotMatchException;
import com.gng.network.exceptions.ServiceException;
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
import java.util.List;

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

    @RequestMapping("/change-password.ajax")
    public String changePasswordAjax() {
        return "inc/change-password";
    }

    @RequestMapping("/privacy.ajax")
    public String privacyAjax() {
        return "inc/privacy";
    }

    @ResponseBody
    @RequestMapping(value = "/process-change-password.ajax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessChangePasswordJson processChangePassword(@Valid UserPassword userPassword, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError("newPassword");
                String messageKey = fieldError.getDefaultMessage();
                String successMessage = messageSource.getMessage(messageKey, null, null);
                return new ProcessChangePasswordJson(successMessage, true);
            }
            if(!ApplicationUtils.validatePasswordMatches(userPassword.getNewPassword(), userPassword.getConfirmNewPassword())) {
                throw new PasswordDoNotMatchException("New Password And Confirm New Password do not match", "reset.password.page.passwords.not.match.message");
            }
            settingsService.validateAndChangeUserPassword(userPassword.getUserId(), userPassword.getCurrentPassword(), userPassword.getNewPassword());
            String successMessage = messageSource.getMessage("password.change.page.password.successfully.changed.message", null, null);
            return new ProcessChangePasswordJson(successMessage, false);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            String errorMessage = messageSource.getMessage(e.getKeyCode(), null, null);
            return new ProcessChangePasswordJson(errorMessage, true);
        } catch (PasswordDoNotMatchException e) {
            log.error(e.getMessage(), e);
            String errorMessage = messageSource.getMessage(e.getKeyCode(), null, null);
            return new ProcessChangePasswordJson(errorMessage, true);
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
    private static class ProcessChangePasswordJson {
        private String message;
        private boolean errorMessage;
    }

}
