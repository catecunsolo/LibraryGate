package com.libraryExtra.controller;

import com.libraryExtra.entity.Role;
import com.libraryExtra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sign-up")
    public ModelAndView signUp(HttpServletRequest request, Principal principal){
        ModelAndView modelAndView = new ModelAndView("sign-up");
        Map<String,?>flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null ){
            modelAndView.addObject("success",flashMap.get("success"));
            modelAndView.addObject("error",flashMap.get("error"));
            modelAndView.addObject("username",flashMap.get("username"));
            modelAndView.addObject("password",flashMap.get("password"));
        }
  if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }
        modelAndView.addObject("action", "sign-in");
        return modelAndView;
    }

    @PostMapping("/sign-in")
    public RedirectView signIn(@RequestParam String username, @RequestParam String password,@RequestParam("user.role") Role role, RedirectAttributes a){
        RedirectView redirectView = new RedirectView("/login"); //chequear esto despues
try{
    userService.add(username,password, role);
    a.addFlashAttribute("success","Signed successfully.");
}catch(Exception e){
    a.addFlashAttribute("error", "Error signing up --> "+e.getMessage());
    a.addFlashAttribute("username",username);
    a.addFlashAttribute("password",password);
    redirectView.setUrl("/user/sign-up");
}
return redirectView;
    }

    @GetMapping("/add")
    public ModelAndView add(HttpServletRequest request, Principal principal){
        ModelAndView modelAndView = new ModelAndView("sign-up");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            modelAndView.addObject("success", flashMap.get("success"));
            modelAndView.addObject("error", flashMap.get("error"));
            modelAndView.addObject("username", flashMap.get("username"));
            modelAndView.addObject("password", flashMap.get("password"));
        }
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String username, @RequestParam String password, @RequestParam("user.role") Role role, RedirectAttributes a) {
        RedirectView redirectView = new RedirectView("/");
        try {
           userService.add(username,password,role);
            a.addFlashAttribute("success", "User added successfully.");
        } catch (Exception e) {
           a.addFlashAttribute("error", "Error saving user --> "+e.getMessage());
            a.addFlashAttribute("username", username);
            a.addFlashAttribute("password", password);
            redirectView.setUrl("/users/add");
        }
        return redirectView;
    }

}
