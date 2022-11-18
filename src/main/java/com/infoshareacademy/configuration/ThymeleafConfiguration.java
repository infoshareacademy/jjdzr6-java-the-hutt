package com.infoshareacademy.configuration;

import com.infoshareacademy.entity.user.User;
import com.infoshareacademy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    public ThymeleafConfiguration(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }
}
