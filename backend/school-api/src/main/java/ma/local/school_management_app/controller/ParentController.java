package ma.local.school_management_app.controller;

import ma.local.school_management_app.academic.dto.ExamResponse;
import ma.local.school_management_app.academic.dto.GradeResponse;
import ma.local.school_management_app.academic.dto.TimetableEntryResponse;
import ma.local.school_management_app.academic.service.ExamService;
import ma.local.school_management_app.academic.service.GradeService;
import ma.local.school_management_app.academic.service.TimetableEntryService;
import ma.local.school_management_app.analytics.dto.StudentAnalyticsResponse;
import ma.local.school_management_app.analytics.service.StudentDashboardService;
import ma.local.school_management_app.user.dto.ParentChildResponse;
import ma.local.school_management_app.user.service.CurrentUserService;
import ma.local.school_management_app.user.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents/me")
public class ParentController {

    private final ParentService parentService;
    private final GradeService gradeService;
    private final StudentDashboardService studentDashboardService;
    private final TimetableEntryService timetableEntryService;
    private final ExamService examService;
    private final CurrentUserService currentUserService;

    public ParentController(
            ParentService parentService,
            GradeService gradeService,
            StudentDashboardService studentDashboardService,
            TimetableEntryService timetableEntryService,
            ExamService examService,
            CurrentUserService currentUserService
    ) {
        this.parentService = parentService;
        this.gradeService = gradeService;
        this.studentDashboardService = studentDashboardService;
        this.timetableEntryService = timetableEntryService;
        this.examService = examService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/children")
    public ResponseEntity<List<ParentChildResponse>> getMyChildren(Authentication authentication) {
        Long userId = extractUserId(authentication);
        return ResponseEntity.ok(parentService.getChildrenByUserId(userId));
    }

    @GetMapping("/children/{studentId}/grades")
    public ResponseEntity<List<GradeResponse>> getChildGrades(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        parentService.validateParentOwnsStudent(userId, studentId);
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }

    @GetMapping("/children/{studentId}/analytics")
    public ResponseEntity<StudentAnalyticsResponse> getChildAnalytics(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        parentService.validateParentOwnsStudent(userId, studentId);
        return ResponseEntity.ok(studentDashboardService.getStudentAnalytics(studentId));
    }

    @GetMapping("/children/{studentId}/timetable")
    public ResponseEntity<List<TimetableEntryResponse>> getChildTimetable(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        parentService.validateParentOwnsStudent(userId, studentId);
        return ResponseEntity.ok(timetableEntryService.getTimetableByStudent(studentId));
    }

    @GetMapping("/children/{studentId}/exams")
    public ResponseEntity<List<ExamResponse>> getChildExams(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        Long userId = extractUserId(authentication);
        parentService.validateParentOwnsStudent(userId, studentId);
        return ResponseEntity.ok(examService.getExamsByStudent(studentId));
    }

    private Long extractUserId(Authentication authentication) {
        String email = authentication.getName();
        return currentUserService.getUserIdByEmail(email);
    }
}