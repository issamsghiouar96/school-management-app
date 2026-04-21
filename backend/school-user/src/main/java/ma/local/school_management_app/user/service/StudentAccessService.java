package ma.local.school_management_app.user.service;

import ma.local.school_management_app.common.exception.ResourceNotFoundException;
import ma.local.school_management_app.user.entity.Student;
import ma.local.school_management_app.user.entity.User;
import ma.local.school_management_app.user.repository.StudentRepository;
import ma.local.school_management_app.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentAccessService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public StudentAccessService(
            UserRepository userRepository,
            StudentRepository studentRepository
    ) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    public void validateStudentAccess(String authenticatedEmail, Long requestedStudentId) {
        User user = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));

        if (!"STUDENT".equals(user.getRole().name())) {
            return;
        }

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        if (!student.getId().equals(requestedStudentId)) {
            throw new ResourceNotFoundException("You are not allowed to access this student");
        }
    }
}