package ma.local.school_management_app.auth.service;

import ma.local.school_management_app.auth.dto.LoginRequest;
import ma.local.school_management_app.auth.dto.LoginResponse;
import ma.local.school_management_app.common.exception.ResourceNotFoundException;
import ma.local.school_management_app.user.entity.Student;
import ma.local.school_management_app.user.entity.User;
import ma.local.school_management_app.user.repository.UserRepository;
import ma.local.school_management_app.user.repository.StudentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final CustomUserDetailsService userDetailsService;
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final StudentRepository studentRepository;

        public AuthService(
                        AuthenticationManager authenticationManager,
                        CustomUserDetailsService userDetailsService,
                        JwtService jwtService,
                        UserRepository userRepository,
                        StudentRepository studentRepository) {
                this.authenticationManager = authenticationManager;
                this.userDetailsService = userDetailsService;
                this.jwtService = jwtService;
                this.userRepository = userRepository;
                this.studentRepository = studentRepository;
        }

        public LoginResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
                String token = jwtService.generateToken(userDetails);

                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                Long studentId = null;

                if (user.getRole().name().equals("STUDENT")) {
                        Student student = studentRepository.findByUserId(user.getId())
                                        .orElseThrow(() -> new RuntimeException("Student not found"));
                        studentId = student.getId();
                }
                System.out.println("DEBUG studentId = " + studentId);
                return new LoginResponse(
                                token,
                                user.getId(),
                                user.getEmail(),
                                user.getRole().name(),
                                user.getFirstName(),
                                user.getLastName(),
                                studentId
                        );
        }
}