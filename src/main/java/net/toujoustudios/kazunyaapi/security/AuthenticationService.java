package net.toujoustudios.kazunyaapi.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.toujoustudios.kazunyaapi.dto.request.AuthenticationRequest;
import net.toujoustudios.kazunyaapi.dto.request.RegisterRequest;
import net.toujoustudios.kazunyaapi.dto.response.AuthenticationResponse;
import net.toujoustudios.kazunyaapi.repository.UserRepository;
import net.toujoustudios.kazunyaapi.type.UserRole;
import net.toujoustudios.kazunyaapi.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request, HttpServletRequest httpServletRequest) {
        if (repository.findByUsername(request.getUsername()).isPresent())
            throw new IllegalArgumentException("The username is already taken.");
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .fullName(request.getFullName())
                .registerIp(httpServletRequest.getRemoteAddr())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().id(user.getId()).token(jwtToken).role(user.getRole()).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().id(user.getId()).token(jwtToken).role(user.getRole()).build();
    }

}