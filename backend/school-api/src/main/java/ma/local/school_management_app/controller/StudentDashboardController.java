package ma.local.school_management_app.controller;

import ma.local.school_management_app.academic.dto.GradeResponse;
import ma.local.school_management_app.analytics.dto.StudentAnalyticsResponse;
import ma.local.school_management_app.analytics.service.StudentDashboardService;
import ma.local.school_management_app.user.service.StudentAccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentDashboardController {

    private final StudentDashboardService studentDashboardService;
    private final StudentAccessService studentAccessService;

    public StudentDashboardController(
            StudentDashboardService studentDashboardService,
            StudentAccessService studentAccessService
    ) {
        this.studentDashboardService = studentDashboardService;
        this.studentAccessService = studentAccessService;
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<GradeResponse>> getStudentGrades(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        studentAccessService.validateStudentAccess(authentication.getName(), studentId);
        return ResponseEntity.ok(studentDashboardService.getStudentGrades(studentId));
    }

    @GetMapping("/{studentId}/analytics")
    public ResponseEntity<StudentAnalyticsResponse> getStudentAnalytics(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        studentAccessService.validateStudentAccess(authentication.getName(), studentId);
        return ResponseEntity.ok(studentDashboardService.getStudentAnalytics(studentId));
    }
}