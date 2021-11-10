package com.libraryExtra.controller;

import com.libraryExtra.entity.Author;
import com.libraryExtra.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @GetMapping("/trial")
    public String trialGet(){
        return "Por aquí andamos probando 1 2 3";
    }

    //test
/*    @GetMapping("/get-all-void")
    public void getAuthorsVoid(){
        authorService.findAuthors();
    }*/

    @GetMapping("/add")
    public ModelAndView addAuthor(){
        ModelAndView modelAndView = new ModelAndView("author-form");
        modelAndView.addObject("author",new Author());
        modelAndView.addObject("title","Add Author");
        modelAndView.addObject("action","save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name, RedirectAttributes a) throws Exception{
        try {
            authorService.createAuthor(name);
            a.addFlashAttribute("success","Author added successfully.");
        }catch(Exception e) {
            a.addFlashAttribute("error","Error saving author  --> "+e.getMessage());
        }
        return new RedirectView("/author/get-all");
    }

    @GetMapping("/get-all")
    public ModelAndView getAuthors(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("author");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if(map!=null){
            modelAndView.addObject("error", map.get("error"));
        }
        modelAndView.addObject("authors", authorService.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView edit(@RequestParam Integer id, @RequestParam String name){
        //authorService.editName(authorService.findAuthor(id), name);
        authorService.edit(id,name);
        return new RedirectView("/author/get-all");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editAuthor(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("author-form");
        modelAndView.addObject("author",authorService.findAuthor(id));
        modelAndView.addObject("title","Edit Author");
        modelAndView.addObject("action","edit");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id){
        authorService.delete(id);
        return new RedirectView("/author/get-all");
    }

    @PostMapping("/deActivate/{id}")
    public RedirectView deActivate(@PathVariable Integer id){
        authorService.deActivate(id);
        return new RedirectView("/author/get-all");
    }

}
