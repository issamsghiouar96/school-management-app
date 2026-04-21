package ma.local.school_management_app.controller;

import jakarta.validation.Valid;
import ma.local.school_management_app.auth.dto.LoginRequest;
import ma.local.school_management_app.auth.dto.LoginResponse;
import ma.local.school_management_app.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}