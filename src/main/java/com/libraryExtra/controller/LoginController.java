package com.libraryExtra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    //private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("login");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
        }
        if(error != null ){
            modelAndView.addObject("error", "Incorrect username/password.");
        }
        if(logout != null){
            modelAndView.addObject("logout", "Session finished.");
        }
        if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
