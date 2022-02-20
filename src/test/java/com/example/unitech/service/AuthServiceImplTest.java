package com.example.unitech.service;

import com.example.unitech.exception.PinAlreadyExsistException;
import com.example.unitech.repository.AccountRepository;
import com.example.unitech.repository.UserRepository;
import com.example.unitech.request.LoginRequest;
import com.example.unitech.request.SignUpRequest;
import com.example.unitech.security.jwt.JwtUtils;
import com.example.unitech.security.services.UserDetailsImpl;
import com.example.unitech.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    private static final String PIN = "123456";
    private static final String PASSWORD = "1234564";
    private static final String JWT = "2a$10$hqnMvosAs/E4N7pD29";
    private static final String PASSWORD_ENCODER = "$2a$10$hqnMvosAs/E4N7pD293YOeddcw3NqA/GMbDbkPygvb00rxxzEAh4u";

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;

    SignUpRequest signUpRequest;
    LoginRequest loginRequest;
    Authentication authentication;
    UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        signUpRequest = SignUpRequest.builder()
                .pin(PIN)
                .password(PASSWORD)
                .build();
        loginRequest = LoginRequest.builder()
                .password(PASSWORD)
                .pin(PIN)
                .build();
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPin(), loginRequest.getPassword()));
    }

    @Test
    void registerUserThenSuccess() {
        //arrange
        when(userRepository.existsByPin(anyString())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn(PASSWORD_ENCODER);

        //act
        authService.registerUser(signUpRequest);

        //assert
        verify(userRepository, times(1)).existsByPin(anyString());
    }

    @Test
    void registerUserThenPingAlreadyExistException() {
        //arrange
        when(userRepository.existsByPin(anyString())).thenReturn(true);

        //act & assert
        assertThrows(PinAlreadyExsistException.class, () ->
                authService.registerUser(signUpRequest));
    }

}
