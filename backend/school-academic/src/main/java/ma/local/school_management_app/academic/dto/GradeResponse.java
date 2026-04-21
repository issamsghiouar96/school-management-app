package ma.local.school_management_app.academic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GradeResponse {

    private Long id;
    private Double value;
    private String semester;
    private LocalDateTime createdAt;

    private Long studentId;
    private String studentCode;
    private String studentFullName;

    private Long teacherId;
    private String teacherFullName;

    private Long subjectId;
    private String subjectName;
}