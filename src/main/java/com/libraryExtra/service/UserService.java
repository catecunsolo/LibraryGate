package com.libraryExtra.service;

import com.libraryExtra.entity.Author;
import com.libraryExtra.entity.Role;
import com.libraryExtra.entity.UserLogin;
import com.libraryExtra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService  implements UserDetailsService {

    private UserLogin userLogin;

@Autowired
private UserRepository userRepository;

@Autowired
private BCryptPasswordEncoder encoder;

private final String MESSAGE="This user does not exist. %s";

@Transactional
public void add(String username, String password, Role role) throws Exception { //create
validateUsername(username);
validatePassword(password);
userLogin = new UserLogin();
userLogin.setUsername(username);
userLogin.setPassword(encoder.encode(password));
userLogin.setRole(role);
userRepository.save(userLogin);
    }

/*    @Transactional(readOnly = true)
    public UserLogin addUser(String username, String password) throws Exception{
        add(username, password);
        Optional<UserLogin> userOptional=userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }*/

    @Transactional(readOnly = true)
    public UserLogin addUser(String username, String password, Role role) throws Exception{
        add(username,password,role);
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(MESSAGE,username)));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+userLogin.getRole().getName());
        return new User(userLogin.getUsername(),userLogin.getPassword(), Collections.singletonList(authority)); //Collections.emptyList()
    }

    public void validateUsername(String username) throws Exception {
        if (username == null) {
            throw new Exception("Username field cannot be empty.");
        }
     if (username.length() <7) {
         throw new Exception("The username must contain 8 digits at least.");
     }
        if (userRepository.existsUserLoginByUsername(username)) {
            throw new Exception("This username already exists.");
        }
/*        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic((name.charAt(i)))) {
                throw new Exception("The name must not contain numbers.");
            }
        }*/
    }


    public void validatePassword(String password) throws Exception {
    if(password==null){
        throw new Exception("The password must not be empty.");
    }
    if(password.length()<7){
        throw new Exception ("The password must contain 8 characters at least.");
    }
   // boolean containNumber=password.matches("[+-]?\\d*(\\.\\d+)?");

    }
}
