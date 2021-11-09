package com.libraryExtra.controller;

import com.libraryExtra.entity.Client;
import com.libraryExtra.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/add")
    public ModelAndView addBook(){
        ModelAndView modelAndView = new ModelAndView("client-form");
        modelAndView.addObject("client", new Client());
        modelAndView.addObject("title", "Add Client");
        modelAndView.addObject("action","save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam Long dni, @RequestParam String name, @RequestParam String surname, @RequestParam String phoneNumber, RedirectAttributes a) throws Exception {
        try {
            clientService.createClient(dni, name, surname, phoneNumber);
            a.addFlashAttribute("success", "Client added successfully.");
        } catch (Exception e) {
            a.addFlashAttribute("error", "Error adding client--> " + e.getMessage());
            return new RedirectView("/client/add");
        }
        return new RedirectView("/client/get-all");
    }

    @GetMapping("/get-all")
    public ModelAndView getClients(){
        ModelAndView modelAndView = new ModelAndView("client");
        modelAndView.addObject("clients",clientService.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView edit(@RequestParam String id, @RequestParam String name, @RequestParam String surname, @RequestParam String phoneNumber, RedirectAttributes a){
        try {
            clientService.edit(id, name, surname, phoneNumber);
            a.addFlashAttribute("success", "Client modified successfully.");
        }catch(Exception e) {
            a.addFlashAttribute("error", "Error modifying client --> "+ e.getMessage());
        }
        return new RedirectView("/client/get-all");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editClient(@PathVariable String id){
        ModelAndView modelAndView= new ModelAndView("client-form");
        modelAndView.addObject("client", clientService.findClient(id));
        modelAndView.addObject("title", "Edit Client");
        modelAndView.addObject("action", "edit");
        return modelAndView;
    }

    @PostMapping("/deActivate/{id}")
    public RedirectView deActivate(@PathVariable String id){
        clientService.deActivate(id);
        return new RedirectView("/client/get-all");
    }

}
