package com.libraryExtra.controller;

import com.libraryExtra.entity.Editorial;
import com.libraryExtra.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/editorial")
public class EditorialController {
    @Autowired
    EditorialService editorialService;

    @GetMapping("/add")
    public ModelAndView addEditorial() {
        ModelAndView modelAndView = new ModelAndView("editorial-form");
        modelAndView.addObject("editorial",new Editorial());
        modelAndView.addObject("title","Add Editorial");
        modelAndView.addObject("action","save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name, RedirectAttributes a) throws Exception{
    try {
        editorialService.createEditorial(name);
        a.addFlashAttribute("success","Editorial saved successfully.");
    }catch(Exception e){
        a.addFlashAttribute("error", "Error saving editorial --> "+e.getMessage());
    }
    return new RedirectView("/editorial/get-all");
    }

    @GetMapping("/get-all")
    public ModelAndView getAuthors() {
        ModelAndView modelAndView = new ModelAndView("editorial");
        modelAndView.addObject("editorials", editorialService.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView edit(@RequestParam Integer id, @RequestParam String name){
        editorialService.edit(id,name);
        return new RedirectView("/editorial/get-all");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editEditorial(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("editorial-form");
        modelAndView.addObject("editorial",editorialService.findEditorial(id));
        modelAndView.addObject("title","Edit Editorial");
        modelAndView.addObject("action","edit");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id){
        editorialService.delete(id);
        return new RedirectView("/editorial/get-all");
    }

    @PostMapping("/deActivate/{id}")
    public RedirectView deActivate(@PathVariable Integer id){
        editorialService.deActivate(id);
        return new RedirectView("/editorial/get-all");
    }

}
