package com.brijframwork.authorization.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers("/api/","api/csrf","/actuator/*",
        		"/swagger-resources/**",
        		"/api/swagger-resources",
        		"/api/swagger-resources/**",
        		"/api/configuration/ui",
        		"/api/webjars/**",
        		"/api/swagger-ui.html",
        		"/api/configuration/security",
        		"/v2/api-docs",
        		"/api/v2/api-docs","oauth/authorize").permitAll()
	    .anyRequest().authenticated()
	    .and()
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}