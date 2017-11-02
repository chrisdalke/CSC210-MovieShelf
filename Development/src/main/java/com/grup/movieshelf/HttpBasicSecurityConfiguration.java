package com.grup.movieshelf;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Configuration
@Order(1)
public class HttpBasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public class ApiRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches( HttpServletRequest request) {
            final String path = request.getServletPath();
            System.out.println("Examining url: "+path);
            return (path != null && path.startsWith("/api/"));
        }
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**").authorizeRequests().anyRequest().permitAll().and().httpBasic();
    }
}
