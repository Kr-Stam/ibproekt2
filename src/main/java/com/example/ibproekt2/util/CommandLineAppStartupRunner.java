package com.example.ibproekt2.util;

import com.example.ibproekt2.entity.User;
import com.example.ibproekt2.repository.UserRepository;
import com.example.ibproekt2.security.Role;
import com.example.ibproekt2.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        User admin = User.builder()
                ._username("admin")
                .email("mejlzatestiranje041@gmail.com")
                .password(passwordEncoder.encode("123"))
                .role(Role.ROLE_ADMIN)
                .accountVerified(true)
                .build();

        if(userRepository.findByEmail(admin.getEmail()).isEmpty()){
            userRepository.save(admin);
        }
    }
}
