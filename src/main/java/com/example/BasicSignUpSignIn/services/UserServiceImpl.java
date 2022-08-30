package com.example.BasicSignUpSignIn.services;

import com.example.BasicSignUpSignIn.data.models.User;
import com.example.BasicSignUpSignIn.data.models.Role;
import com.example.BasicSignUpSignIn.data.repositories.RoleRepository;
import com.example.BasicSignUpSignIn.data.repositories.UserRepository;
import com.example.BasicSignUpSignIn.dtos.requests.AddRoleRequest;
import com.example.BasicSignUpSignIn.dtos.requests.AddRoleToUserRequest;
import com.example.BasicSignUpSignIn.dtos.requests.SignUpRequest;
import com.example.BasicSignUpSignIn.web.exception.UserAlreadyExistsException;
import com.example.BasicSignUpSignIn.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j @Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> searchedUser = userRepository.findUserByEmail(username);
        if (searchedUser.isEmpty()){
            throw new UsernameNotFoundException("User not found in the db");
        }
        User user = searchedUser.get();
        log.info("User {} found", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
    @Override
    public User register(SignUpRequest request) throws UserAlreadyExistsException {
        log.info("Saving user into the database");
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if(user.isPresent()){
            throw new UserAlreadyExistsException(request.getEmail()+" already registered");
        }

        User newUser = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public Role saveRole(AddRoleRequest request) {
        log.info("Saving role {} into the database", request.getRoleName());
        Role role = new Role(request.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(AddRoleToUserRequest request) throws UserNotFoundException {
        log.info("Adding role {} to user {} in the database", request.getRoleName(), request.getEmail());
        User user = userRepository.findUserByEmail( request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findRoleByName(request.getRoleName());
        if(role == null){
            role = new Role(request.getRoleName());
            roleRepository.save(role);
        }
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String email) {
        log.info("searching for user {}", email);
        return userRepository.findUserByEmail(email).get();
    }

    @Override
    public List<User> getAllUSer() {
        log.info("getting all User in the database");
        return userRepository.findAll();
    }

}
