package com.hcl.capstoneserver.config;

import com.hcl.capstoneserver.filters.JwtRequestFilter;
import com.hcl.capstoneserver.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final JwtRequestFilter jwtRequestFilter;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfigurer(
            UserService userService, JwtRequestFilter jwtRequestFilter,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/favicon.ico").permitAll()
            .antMatchers("/api/sign-in").permitAll()
            .antMatchers("/api/refresh-token").permitAll()
            .antMatchers("/api/sign-up").permitAll()
            .antMatchers("/api/auth/sign-up/client").permitAll()
            .antMatchers("/api/auth/sign-up/supplier").permitAll()
            .antMatchers("/api/getFile/**").permitAll()
            .antMatchers("/api/user/forgotPassword/getOTP").permitAll()
            .antMatchers("/api/user/forgotPassword/verifyUser").permitAll()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
