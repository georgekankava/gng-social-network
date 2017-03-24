package com.gng.network.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by georgekankava on 24.03.17.
 */
@Controller
public class SettingsController {

    @RequestMapping("/settings")
    public String loadSecurityPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "settings";
    }
}
