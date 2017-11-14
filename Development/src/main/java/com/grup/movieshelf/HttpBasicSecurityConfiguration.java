///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import javax.servlet.http.HttpServletRequest;

/////////////////////////////////////////////////////////////
// HttpBasicSecurityConfiguration
// Overrides the default security filter to provide custom
// stateless authentication for REST api calls.
/////////////////////////////////////////////////////////////

@Configuration
@Order(1)
public class HttpBasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

    //------------------------------------------------
    // ApiRequestMatcher
    // A pattern matcher that matches any API endpoints.
    //------------------------------------------------

    public class ApiRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches( HttpServletRequest request) {
            final String path = request.getServletPath();
            System.out.println("Examining url: "+path);
            return (path != null && path.startsWith("/api/"));
        }
    }

    //------------------------------------------------
    // Security Configurer
    // Builds the security context for this filter.
    //------------------------------------------------

    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeRequests().anyRequest().permitAll().and().httpBasic();
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////