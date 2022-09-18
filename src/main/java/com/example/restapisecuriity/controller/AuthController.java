package com.example.restapisecuriity.controller;

import com.example.restapisecuriity.config.jwt.JwtProvider;
import com.example.restapisecuriity.dto.AuthDto;
import com.example.restapisecuriity.dto.AuthResponse;
import com.example.restapisecuriity.dto.IntrospectRequest;
import com.example.restapisecuriity.entity.User;
import com.example.restapisecuriity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    private final UserService service;
    private final JwtProvider provider;

    @Autowired
    public AuthController(UserService service, JwtProvider provider) {
        this.service = service;
        this.provider = provider;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthDto dto){
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        service.register(user);
        return "Ok";
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthDto dto){
        Optional<User> userOptional = service.findByLoginAndPassword(dto.getLogin(), dto.getPassword());

        if(userOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = provider.generatedAccessToken(dto.getLogin());
        String refreshToken = provider.generateRefreshToken(dto.getLogin());

        return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
                HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<AuthResponse> introspect (@RequestBody IntrospectRequest request){
       boolean isValid = provider.validate(request.getRefreshToken());
        if(isValid){
            String login = provider.getLoginFromToken(request.getRefreshToken());
            return getAuth(login);
        }
        return new ResponseEntity<>(
                HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<AuthResponse> getAuth(String login) {
        String accessToken = provider.generatedAccessToken(login);
        String refreshToken = provider.generateRefreshToken(login);

        return new ResponseEntity<>(new AuthResponse(accessToken, refreshToken),
                HttpStatus.OK);
    }
}
