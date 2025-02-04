package com.mailman.auth_service.service;

import com.mailman.auth_service.Util.JwtUtil;
import com.mailman.auth_service.entity.User;
import com.mailman.auth_service.models.LoginRequest;
import com.mailman.auth_service.models.RegisterRequest;
import com.mailman.auth_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtUtil jwtUtil;

    private final  UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //Register new user
    public void registerUser(RegisterRequest registerRequest) {
        if(userRepository.findByUserName(registerRequest.getUserName()) != null) {
            log.info("already user found");
            throw new IllegalArgumentException("User with userName or email already exists");
        }
        User newUser = new User();
        newUser.setUserName(registerRequest.getUserName());
        newUser.setUserPassword(bCryptPasswordEncoder.encode(registerRequest.getUserPassword()));
        userRepository.save(newUser);

    }

    //Authenticate user and generate JWT token
    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUserName(loginRequest.getUserName());
        if (user == null || !bCryptPasswordEncoder.matches(loginRequest.getUserPassword(),user.getUserPassword())){
            throw new IllegalArgumentException("Invalid userName or password");
        }
        return jwtUtil.generateToken(user.getUserName());
    }

    public List<User> getAllRegisteredUsers() {
        return userRepository.findAll();
    }
}
