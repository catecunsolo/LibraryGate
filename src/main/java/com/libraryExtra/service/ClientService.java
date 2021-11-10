package com.libraryExtra.service;

import com.libraryExtra.entity.Client;
import com.libraryExtra.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private Client client;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public void createClient(Long dni, String name, String surname, String phoneNumber) throws Exception {
        client= new Client();
        checkPhone(phoneNumber);
        checkDNI(dni);
        checkName(name,surname);
        client.setDni(dni);
        client.setName(name);
        client.setSurname(surname);
        client.setPhoneNumber(phoneNumber);
        client.setAvailable(true);
        clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client findClient(String id){
        Optional<Client> clientOptional=clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Client>findAll(){ return clientRepository.findAll();}

    @Transactional
    public void edit(String id, String name, String surname, String phoneNumber) throws Exception {
        checkName(name,surname);
        checkPhone(phoneNumber);
        clientRepository.modify(id,name,surname,phoneNumber);
    }

    @Transactional
    public void deActivate(String id){
        if(clientRepository.findById(id).get().getAvailable()==true){
            clientRepository.deActivate(id, false);
        }else{
            clientRepository.deActivate(id, true);
        }
    }

    public void checkDNI(Long dni) throws Exception {
        if(clientRepository.findDNI(dni)!=null){
            throw new Exception("This dni is already registered.");
        }
        if(dni.toString().length()<7||dni.toString().length()>8){
            throw new Exception("Error with dni's digits.");
        }
    }

    public void checkPhone(String phoneNumber) throws Exception {
        if(phoneNumber.length()<7){
            throw new Exception("The phone number must contain 8 digits at least.");
        }
    }

    public void checkName(String name,String surname) throws Exception {
        if(name==null||name.trim().isEmpty()||surname==null||surname.trim().isEmpty()){
            throw new Exception ("A name/surname must be included.");
        }
    }

}

