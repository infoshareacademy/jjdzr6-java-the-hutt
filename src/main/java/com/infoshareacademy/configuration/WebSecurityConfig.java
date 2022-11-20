package com.infoshareacademy.configuration;

import com.infoshareacademy.service.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImplementation userDetailsServiceImplementation) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImplementation);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/sign-in", "/sign-up", "/register", "/logout", "/").permitAll()
                .antMatchers("/recipes/**", "/food-preferences/**","/fridge/**", "/shopping-lists/**" ).authenticated()
                .and()
                .formLogin()
                .loginPage("/sign-in")
                .loginProcessingUrl("/perform-login")
                .defaultSuccessUrl("/recipes")
                .failureUrl("/sign-in?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/sign-in?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .rememberMe();

    }
}
