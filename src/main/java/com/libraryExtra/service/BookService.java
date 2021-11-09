package com.libraryExtra.service;

import com.libraryExtra.entity.Book;
import com.libraryExtra.repository.AuthorRepository;
import com.libraryExtra.repository.BookRepository;
import com.libraryExtra.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private Book book;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional
    public Book createBook(Long isbn, String title, Integer year, Integer copies, Integer copiesLeft, Integer authorID, Integer editorialID) throws Exception {
        Integer copiesLoaned;
        validate(isbn, title, year, copies, copiesLeft);
        book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setYear(year);
        if(copies==copiesLeft){
            copiesLoaned=0;
        }else{
            copiesLoaned=copies-copiesLeft;
        }
        book.setCopies(copies);
        book.setCopiesLoaned(copiesLoaned);
        book.setCopiesLeft(copiesLeft);
        book.setAvailable(true);
        book.setAuthor(authorRepository.findById(authorID).orElse(null));
        //book.setEditorial(editorialService.findEditorial(editorialID));
        book.setEditorial(editorialRepository.findById(editorialID).orElse(null));
        bookRepository.save(book);
        return book;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findBook(Integer id){
        Optional<Book> bookOptional=bookRepository.findById(id); //idk por que aca no me toma el integer y en otros Service, si
        return bookOptional.orElse(null);
    }

    @Transactional
    public void delete(Integer id){bookRepository.deleteById(id);}

    @Transactional
    public void edit(Integer id, String title, Integer year, Integer copiesLeft, Integer authorID, Integer editorialID){
        Book book = findBook(id);
        book.setTitle(title);
        book.setYear(year);
        book.setCopiesLeft(copiesLeft);
        book.setAuthor(authorRepository.findById(authorID).orElse(null));
        book.setEditorial(editorialRepository.findById(editorialID).orElse(null));
        bookRepository.save(book);
    }

    @Transactional
    public void deActivate(Integer id) {
        if(bookRepository.findById(id).get().getAvailable()==true){
            bookRepository.deActivate(id,false);
        } else{
            bookRepository.deActivate(id,true);
        }
    }

    public void validate(Long isbn, String title, Integer year, Integer copies, Integer copiesLeft ) throws Exception{
        if(isbn==null||title==null||title.trim().isEmpty()||year==null||copies==null||copiesLeft==null){
            throw new Exception("No empty fields allowed.");
        }
        validateIsbn(isbn);
        if(copiesLeft>copies){
            throw new Exception("Check number of copies/copies left.");
        }
        if(bookRepository.findBookByIsbn(isbn)!=null){
            throw new Exception("A book with this isbn already exists.");
        }
        if(year>2022){
            throw new Exception("Check the year of release.");
        }
    }

    public void validateIsbn(Long isbn) throws Exception{
      boolean isLong = isbn.toString().chars().allMatch(Character :: isDigit);
      if(isLong==false){
          throw new Exception ("The isbn must not contain letters.");
      }
    }
}
