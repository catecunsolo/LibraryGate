package com.libraryExtra.controller;

import com.libraryExtra.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("role-form");
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name){
        roleService.add(name);
        return new RedirectView("/home");
    }
}
