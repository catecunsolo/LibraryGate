package com.libraryExtra.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping(value="/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView errors(HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("error");
        String message ="";
        int code = response.getStatus();
        switch(code){
            case 403:
                message = "The resource that you're trying to open in your web browser is a resource that you're not allowed to access.";
                break;
            case 404:
                message = "The server could not find the requested website.";
            case 500:
                message ="Internal server error.";
                break;
            default:
                message = "Unexpected error.";
                break;
        }
        modelAndView.addObject("message",message);
        modelAndView.addObject("code",code);
        return modelAndView;
    }

}
