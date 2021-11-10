package com.libraryExtra.controller;

import com.libraryExtra.entity.Book;
import com.libraryExtra.service.AuthorService;
import com.libraryExtra.service.BookService;
import com.libraryExtra.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private EditorialService editorialService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/add")
    public ModelAndView addBook(RedirectAttributes a){ /*HttpServletRequest request*/
        ModelAndView modelAndView = new ModelAndView("book-form");
     //   Map<String,?> flashMap = RequestContextUtils.getInputFlashMap(request);
        modelAndView.addObject("book",new Book());
        modelAndView.addObject("book",new Book());
        modelAndView.addObject("authors",authorService.findAll());
        modelAndView.addObject("editorials",editorialService.findAll());
        modelAndView.addObject("title","Add Book");
        modelAndView.addObject("action","save");
        return modelAndView;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam Long isbn, @RequestParam String title, @RequestParam Integer year, @RequestParam Integer copies, @RequestParam Integer copiesLeft, @RequestParam("author") Integer authorID, @RequestParam("editorial") Integer editorialID, RedirectAttributes a) throws Exception{
        try{
            bookService.createBook(isbn,title,year, copies, copiesLeft,authorID,editorialID); //CHEQUEAR VALIDACION ISBN
            a.addFlashAttribute("success", "Book added successfully.");
        }catch(Exception e){
            a.addFlashAttribute("error","Error saving book --> "+ e.getMessage());
/*            a.addFlashAttribute("isbn",isbn);
            a.addFlashAttribute("title",title);
            a.addFlashAttribute("year",year);
            return new RedirectView("/book/add");*/
        }
        return new RedirectView("/book/get-all");
    }

    @GetMapping("/get-all")
    public ModelAndView getBooks(){
        ModelAndView modelAndView = new ModelAndView("book");
        modelAndView.addObject("books",bookService.findAll());
        return modelAndView;
    }

    @PostMapping("/edit")
    public RedirectView edit(@RequestParam Integer id,@RequestParam String title, @RequestParam Integer year, @RequestParam Integer copiesLeft,@RequestParam("author") Integer authorID,@RequestParam("editorial") Integer editorialID){
        bookService.edit(id, title, year, copiesLeft, authorID, editorialID);
        return new RedirectView("/book/get-all");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editBook(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("book-form");
        modelAndView.addObject("book",bookService.findBook(id));
        modelAndView.addObject("authors",authorService.findAvailable());
        modelAndView.addObject("editorials",editorialService.findAvailable());
        modelAndView.addObject("title","Edit Book");
        modelAndView.addObject("action","edit");
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id){
        bookService.delete(id);
        return new RedirectView("/book/get-all");
    }

    @PostMapping("/deActivate/{id}")
    public RedirectView deActivate(@PathVariable Integer id){
        bookService.deActivate(id);
        return new RedirectView("/book/get-all");
    }

}
