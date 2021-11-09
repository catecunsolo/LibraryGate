package com.libraryExtra.controller;

import com.libraryExtra.entity.Loan;
import com.libraryExtra.service.BookService;
import com.libraryExtra.service.ClientService;
import com.libraryExtra.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public ModelAndView addLoan(){
        ModelAndView modelAndView = new ModelAndView("loan-form");
        modelAndView.addObject("loan", new Loan());
        modelAndView.addObject("clients", clientService.findAll());
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.addObject("title","Add Loan");
        modelAndView.addObject("action", "save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam("book") Integer idBook, @RequestParam("client") String idClient, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnDate, RedirectAttributes a) throws Exception {
        try {
            loanService.createLoan(idBook, idClient, returnDate);
            a.addFlashAttribute("success", "Loan generated successfully.");
        } catch (Exception e) {
            a.addFlashAttribute("error", "Error generating loan --> "+ e.getMessage());
        }
        return new RedirectView("/loan/get-all");
    }

    @GetMapping("/get-all")
    public ModelAndView getLoans(){
        ModelAndView modelAndView = new ModelAndView("loan");
        modelAndView.addObject("loans",loanService.findAll());
        return modelAndView;
    }

    @PostMapping("/deActivate/{id}")
   // public RedirectView deActivate(@RequestParam String id, RedirectAttributes a) throws Exception{
    public RedirectView deActivate(@PathVariable String id, RedirectAttributes a) throws Exception{
        try{
            loanService.deActivate(id);
            if(loanService.findLoan(id).getAvailable()==false) {
                a.addFlashAttribute("success", "Loan deactivated successfully.");
            }else{
                a.addFlashAttribute("success","Loan activated successfully.");
            }
        }catch(Exception e){
            a.addFlashAttribute("error", "Error de/activating loan --> "+e.getMessage());

        }
        return new RedirectView("/loan/get-all");
    }

/*    @GetMapping("/deActivate/{id}")
    public ModelAndView deActivateLoan(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView("loan-form");
        modelAndView.addObject("loan",loanService.findLoan(id));
        modelAndView.addObject("title", "De/Activate");
        modelAndView.addObject("action", "de/activate");
        return modelAndView;
    }*/

}
