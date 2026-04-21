package ma.local.school_management_app.controller;

import jakarta.validation.Valid;
import ma.local.school_management_app.academic.dto.ExamRequest;
import ma.local.school_management_app.academic.dto.ExamResponse;
import ma.local.school_management_app.academic.service.ExamService;
import ma.local.school_management_app.user.service.StudentAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;
    private final StudentAccessService studentAccessService;

    public ExamController(
            ExamService examService,
            StudentAccessService studentAccessService
    ) {
        this.examService = examService;
        this.studentAccessService = studentAccessService;
    }

    @PostMapping
    public ResponseEntity<ExamResponse> createExam(@Valid @RequestBody ExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.createExam(request));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ExamResponse>> getExamsByStudent(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        studentAccessService.validateStudentAccess(authentication.getName(), studentId);
        return ResponseEntity.ok(examService.getExamsByStudent(studentId));
    }
}