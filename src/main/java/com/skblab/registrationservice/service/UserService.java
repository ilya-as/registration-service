package com.skblab.registrationservice.service;

import com.skblab.registrationservice.exception.UserLoginExistsException;
import com.skblab.registrationservice.model.User;
import com.skblab.registrationservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNewUser(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserLoginExistsException(user.getLogin());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
