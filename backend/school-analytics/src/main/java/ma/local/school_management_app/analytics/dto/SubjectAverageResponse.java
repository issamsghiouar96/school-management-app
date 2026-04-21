package ma.local.school_management_app.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubjectAverageResponse {

    private String subjectName;
    private Double average;
}