package com.libraryExtra.service;

import com.libraryExtra.entity.Editorial;
import com.libraryExtra.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {
    private Editorial editorial;
    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional
    public Editorial createEditorial(String name) throws Exception {
        validate(name);
        editorial = new Editorial();
        editorial.setName(name);
        editorial.setAvailable(true);
        editorialRepository.save(editorial);
        return editorial;
    }

    @Transactional
    public Editorial findEditorial(Integer id){
        Optional<Editorial> editorialOptional=editorialRepository.findById(id);
        // return editorialOptional.orElse(createEditorial("Caterina Cunsolo"));
        return editorialOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Editorial> findAll(){return editorialRepository.findAll();}

    @Transactional
    public void edit(Integer id,String name){editorialRepository.edit(id,name);}

    @Transactional
    public void delete(Integer id){editorialRepository.deleteById(id);}

    @Transactional
    public void deActivate(Integer id) {
        if(editorialRepository.findById(id).get().getAvailable()==true){
            editorialRepository.deActivate(id,false);
        } else{
            editorialRepository.deActivate(id,true);
        }
    }

    @Transactional
    public List<Editorial> findAvailable(){
        List<Editorial> availables = new ArrayList<>();
        for(Editorial editorial : editorialRepository.findAll()){
            if(editorial.getAvailable()==true){
                availables.add(editorial);
            }
        }
        return availables;
    }

    public void validate(String name) throws Exception{
        if(editorialRepository.findEditorialByName(name)!=null){
            throw new Exception("This editorial already exists.");
        }
        if(name==null||name.trim().isEmpty()){
            throw new Exception ("A name must be included.");
        }
    }

}
