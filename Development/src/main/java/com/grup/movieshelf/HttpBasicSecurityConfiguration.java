///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/////////////////////////////////////////////////////////////
// HttpBasicSecurityConfiguration
// Overrides the default security filter to provide custom
// stateless authentication for REST api calls.
/////////////////////////////////////////////////////////////

@Configuration
@Order(-1)
public class HttpBasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private RestAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private RestAuthenticationSuccessHandler authenticationSuccessHandler;

    //------------------------------------------------
    // ApiRequestMatcher
    // A pattern matcher that matches any API endpoints.
    //------------------------------------------------

    public class ApiRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches( HttpServletRequest request) {
            final String path = request.getServletPath();
            if (path != null && path.startsWith("/api/")){
                //System.out.println("Path matches api endpoint: "+path);
                return true;
            } else {
                return false;
            }
        }
    }

    //------------------------------------------------
    // Security Configurer
    // Builds the security context for this filter.
    //------------------------------------------------

    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new ApiRequestMatcher()).authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.formLogin().successHandler(authenticationSuccessHandler);
        http.formLogin().failureHandler(authenticationFailureHandler);
        /*
        // /Require HTTP basic auth with every request.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        */
    }


    //------------------------------------------------
    // REST API Authentication Handlers
    // Handlers used to override default login behavior
    // for the REST api endpoints.
    //------------------------------------------------

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public RestAuthenticationSuccessHandler restAuthenticationSuccessHandler(){
        return new RestAuthenticationSuccessHandler();
    }

    @Bean
    public RestAuthenticationFailureHandler restAuthenticationFailureHandler(){
        return new RestAuthenticationFailureHandler();
    }

    @Component
    public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                throws IOException, ServletException {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Component
    public static class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

            clearAuthenticationAttributes(request);
        }
    }

    @Component
    public static class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {

            super.onAuthenticationFailure(request, response, exception);
        }
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////