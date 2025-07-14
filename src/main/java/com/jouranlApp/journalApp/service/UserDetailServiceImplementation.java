package com.jouranlApp.journalApp.service;

import com.jouranlApp.journalApp.Entity.UserEntry;
import com.jouranlApp.journalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UserEntryRepository userEntryRepository;

     public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
         UserEntry user =userEntryRepository.findByUsername(username);
         if(user !=null){
             return User.builder()
                       .username(user.getUsername())
                       .password(user.getPassword())
                       .roles(user.getRoles().toArray(new String[0]))
                       .build();
         }
         else {
             throw  new UsernameNotFoundException("User no found with username"+ username);
         }
     }

}
