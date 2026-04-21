package ma.local.school_management_app.user.service;

import ma.local.school_management_app.common.exception.ResourceNotFoundException;
import ma.local.school_management_app.user.dto.ParentChildResponse;
import ma.local.school_management_app.user.entity.Parent;
import ma.local.school_management_app.user.repository.ParentRepository;
import ma.local.school_management_app.user.repository.ParentStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private final ParentStudentRepository parentStudentRepository;

    public ParentService(
            ParentRepository parentRepository,
            ParentStudentRepository parentStudentRepository
    ) {
        this.parentRepository = parentRepository;
        this.parentStudentRepository = parentStudentRepository;
    }

    public List<ParentChildResponse> getChildrenByUserId(Long userId) {
        Parent parent = parentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        return parentStudentRepository.findByParentId(parent.getId())
                .stream()
                .map(link -> {
                    ParentChildResponse response = new ParentChildResponse();
                    response.setStudentId(link.getStudent().getId());
                    response.setStudentCode(link.getStudent().getStudentCode());
                    response.setClassName(link.getStudent().getClassName());
                    response.setFirstName(link.getStudent().getUser().getFirstName());
                    response.setLastName(link.getStudent().getUser().getLastName());
                    return response;
                })
                .toList();
    }

    public void validateParentOwnsStudent(Long userId, Long studentId) {
        Parent parent = parentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found"));

        boolean allowed = parentStudentRepository.existsByParentIdAndStudentId(parent.getId(), studentId);

        if (!allowed) {
            throw new ResourceNotFoundException("Student not linked to this parent");
        }
    }
}