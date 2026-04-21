package ma.local.school_management_app.academic.dto;

import lombok.Getter;
import lombok.Setter;
import ma.local.school_management_app.common.enums.DayOfWeekEnum;

@Getter
@Setter
public class TimetableEntryResponse {

    private Long id;
    private DayOfWeekEnum dayOfWeek;
    private String startTime;
    private String endTime;
    private String room;

    private Long studentId;
    private String studentCode;
    private String studentFullName;

    private Long teacherId;
    private String teacherFullName;

    private Long subjectId;
    private String subjectName;
}