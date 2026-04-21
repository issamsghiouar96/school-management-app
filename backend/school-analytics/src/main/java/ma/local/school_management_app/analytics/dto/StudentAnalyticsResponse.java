package ma.local.school_management_app.analytics.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentAnalyticsResponse {

    private Long studentId;
    private String studentCode;
    private String studentFullName;

    private Double overallAverage;
    private Double highestGrade;
    private Double lowestGrade;
    private Integer totalGrades;

    private List<SubjectAverageResponse> subjectAverages;
}