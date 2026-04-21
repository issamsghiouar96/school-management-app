package ma.local.school_management_app.academic.dto;

import lombok.Getter;
import lombok.Setter;
import ma.local.school_management_app.common.enums.ExamType;

@Getter
@Setter
public class ExamResponse {

    private Long id;
    private String examDate;
    private ExamType type;
    private String description;

    private Long studentId;
    private String studentCode;
    private String studentFullName;

    private Long subjectId;
    private String subjectName;
}