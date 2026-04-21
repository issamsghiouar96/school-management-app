package ma.local.school_management_app.academic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ma.local.school_management_app.common.enums.ExamType;

@Getter
@Setter
public class ExamRequest {

    @NotNull(message = "Exam date is required")
    private String examDate;

    @NotNull(message = "Exam type is required")
    private ExamType type;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}