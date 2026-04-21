package ma.local.school_management_app.user.service;

import ma.local.school_management_app.common.exception.ResourceNotFoundException;
import ma.local.school_management_app.user.entity.User;
import ma.local.school_management_app.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
        return user.getId();
    }
}