package ma.local.school_management_app.academic.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeRequest {

    @NotNull(message = "Grade value is required")
    @Min(value = 0, message = "Grade must be at least 0")
    @Max(value = 20, message = "Grade must be at most 20")
    private Double value;

    @NotBlank(message = "Semester is required")
    private String semester;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}