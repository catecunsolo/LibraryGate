package com.libraryExtra.service;

import com.libraryExtra.entity.Book;
import com.libraryExtra.entity.Loan;
import com.libraryExtra.repository.BookRepository;
import com.libraryExtra.repository.ClientRepository;
import com.libraryExtra.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
static long miliseconds = System.currentTimeMillis();
    private Loan loan ;
    private Book book;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void createLoan(Integer bookID, String clientID, Date returnDate) throws Exception {
        book= new Book();
        loan= new Loan();
        book=bookRepository.findById(bookID).orElse(null);
        if(book.getCopiesLeft()<1){
            throw new Exception("There are no copies left for this book.");
        }
        book.setCopiesLoaned(book.getCopiesLoaned()+1);
        book.setCopiesLeft(book.getCopiesLeft()-1);
        if(book.getCopiesLeft()<1){
            book.setAvailable(false);
        }
        loan.setAvailable(true);
        Date loanDate = new Date(miliseconds);
        validateDate(loanDate, returnDate);
        loan.setLoanDate(loanDate);
        loan.setReturnDate(returnDate);
        loan.setBook(book);
        loan.setClient(clientRepository.findById(clientID).orElse(null));
        loanRepository.save(loan);
    }

    @Transactional(readOnly = true)
    public List<Loan>findAll(){
        return loanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Loan findLoan(String id){
        Optional<Loan> loanOptional=loanRepository.findById(id);
        return loanOptional.orElse(null);
    }

    @Transactional
    public void deActivate(String id) throws Exception {
/*        if(loanRepository.findById(id).get().getAvailable()==true){
            loanRepository.deActivate(id,false);
        }else{
            loanRepository.deActivate(id,true);
        }*/
        try {
            loan = loanRepository.findById(id).orElse(null);
            if (loanRepository.findById(id).get().getAvailable() == true) {
                Date returnDateToday = new Date(miliseconds);
                loanRepository.deActivate(id, false, returnDateToday);
            } else {
                throw new Exception("The loan is already inactive.");
            }
            book = loan.getBook();
            book.setCopiesLeft(book.getCopiesLeft() + 1);
            book.setCopiesLoaned(book.getCopiesLoaned() - 1);
            if (book.getCopiesLeft() > 0) {
                book.setAvailable(true);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Error de/activating loan (Service)--> "+e.getMessage());
        }
        bookRepository.save(book);
    }

    public void validateDate(Date loanDate, Date returnDate) throws Exception{
if(loanDate.after(returnDate)){
    throw new Exception("Return date must be after loan date.");
}
    }

}
