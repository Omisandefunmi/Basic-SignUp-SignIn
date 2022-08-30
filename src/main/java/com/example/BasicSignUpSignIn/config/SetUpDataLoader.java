package com.example.BasicSignUpSignIn.config;

import com.example.BasicSignUpSignIn.data.models.Role;
import com.example.BasicSignUpSignIn.data.models.User;
import com.example.BasicSignUpSignIn.data.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetUpDataLoader implements ApplicationListener <ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetUpDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(userRepository.findUserByEmail("admin@gmail.com").isEmpty()){
            User adminUser = User.builder()
                    .firstName("admin")
                    .lastName("user")
                    .email("admin@gmail.com")
                    .password("0000")
                    .build();
            log.info("created admin user");
            adminUser.addRole(new Role("user"));
            userRepository.save(adminUser);
        }
    }
}
