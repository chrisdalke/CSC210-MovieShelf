///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/////////////////////////////////////////////////////////////
// WebSecurityConfiguration
// Primary security configuration and filter setup.
// Builds authentication infrastructure for user login.
/////////////////////////////////////////////////////////////

@Configuration
@EnableWebSecurity
@Order(0)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //------------------------------------------------
    // Bean Declarations
    //------------------------------------------------

    // Bean that manages tokens & sessions and automatically saves them to the database
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Bean that provides a way to load user accounts from a database
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    // Bean used to hash passwords using bcrypt algorithm
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    // Bean used to provide Thymeleaf (templating engine) with context about the user's permissions
    // Allows the template to directly check authentication status
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    //------------------------------------------------
    // Security Filter Configuration
    // Configures the filters used to check permissions
    // on endpoints when a request is made.
    //------------------------------------------------

    // Configure the filter to require authentication on most requests, and use login/cookie based authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/error").permitAll()
                    .antMatchers("/public/**").permitAll()
                    .antMatchers("/user/register").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .and()
                .logout()
                .deleteCookies("remember-me")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .rememberMe()
                .and()
                .csrf().disable();
    }

    // Configure the filter to ignore static files
    private static final String[] STATIC_FILES = {"/css/**", "/img/**", "/js/**", "/fonts/**"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(STATIC_FILES); // Ignore any paths matching our list of static file directories
    }

    // Allow cross-origin requests for the REST api
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "OPTIONS" ,"PATCH", "DELETE");
            }
        };
    }

    //------------------------------------------------
    // Authentication Configuration
    // Configures the services used to check
    // username/password when a user logs in.
    //------------------------------------------------

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)  // Use the User Service we declared earlier
                .passwordEncoder(bCryptPasswordEncoder); // Use the Password Encoder we declared earlier
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////