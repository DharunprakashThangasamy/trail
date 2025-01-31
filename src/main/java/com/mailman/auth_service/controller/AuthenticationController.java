package com.mailman.auth_service.controller;

import com.mailman.auth_service.Util.JwtUtil;
import com.mailman.auth_service.models.LoginRequest;
import com.mailman.auth_service.models.RegisterRequest;
import com.mailman.auth_service.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authenticationService.registerUser(registerRequest);
        return ResponseEntity.ok("user successfully registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.loginUser(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateJwt(@RequestHeader("Authorization") String token) {
        if(token != null&& token.startsWith("Bearer ")){
            String jwtToken = token.substring(7);
            String userName = jwtUtil.extractUserName(token);
            if(jwtUtil.validateToken(token,userName)) {
                return ResponseEntity.ok("Token is valid");
            }else {
                return ResponseEntity.status(401).body("Invalid token");
            }

        }
        return ResponseEntity.status(400).body("token is not provided");
    }
}
