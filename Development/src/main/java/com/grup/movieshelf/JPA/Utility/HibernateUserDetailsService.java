package com.grup.movieshelf.JPA.Utility;

import com.grup.movieshelf.JPA.Entity.User;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class HibernateUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private RoleRepository roleRepository;

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
}
