package com.grup.movieshelf.JPA.Utility;

import com.grup.movieshelf.JPA.Entity.User;
import com.grup.movieshelf.JPA.Entity.UserOptions;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserOptionsRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class HibernateUserDetailsService implements UserDetailsService {

   @Autowired
   HibernateSecurityService hibernateSecurityService;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private UserOptionsRepository userOptionsRepository;

   @Autowired
   private RoleRepository roleRepository;

   @Lazy
   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User foundUser = userRepository.findByUsername(username);
      
      if( foundUser == null ) {
         throw new UsernameNotFoundException(username + " was not found.");
      }
      
      return foundUser;
   }

   public void saveNewUser(User user) {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userRepository.save(user);
   }

   /*
   Gets the options for the current user.
    */
   public UserOptions getUserOptionsForUser(){
      User user = hibernateSecurityService.getLoggedInUser();
      if (user == null){
         return null;
      }

      UserOptions userOptions = userOptionsRepository.findByUserId(user.getUserId());

      if(userOptions == null) {
         userOptions = new UserOptions(user);
      }

      return userOptions;
   }

   public void saveUserOptions(UserOptions userOptions) {
      userOptionsRepository.save(userOptions);
   }
}
