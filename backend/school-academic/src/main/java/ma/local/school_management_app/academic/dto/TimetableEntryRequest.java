package ma.local.school_management_app.academic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ma.local.school_management_app.common.enums.DayOfWeekEnum;

@Getter
@Setter
public class TimetableEntryRequest {

    @NotNull(message = "Day of week is required")
    private DayOfWeekEnum dayOfWeek;

    @NotBlank(message = "Start time is required")
    private String startTime;

    @NotBlank(message = "End time is required")
    private String endTime;

    @NotBlank(message = "Room is required")
    private String room;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;
}