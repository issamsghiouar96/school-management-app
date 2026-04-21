package ma.local.school_management_app.controller;

import jakarta.validation.Valid;
import ma.local.school_management_app.academic.dto.TimetableEntryRequest;
import ma.local.school_management_app.academic.dto.TimetableEntryResponse;
import ma.local.school_management_app.academic.service.TimetableEntryService;
import ma.local.school_management_app.user.service.StudentAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
public class TimetableEntryController {

    private final TimetableEntryService timetableEntryService;
    private final StudentAccessService studentAccessService;

    public TimetableEntryController(
            TimetableEntryService timetableEntryService,
            StudentAccessService studentAccessService
    ) {
        this.timetableEntryService = timetableEntryService;
        this.studentAccessService = studentAccessService;
    }

    @PostMapping
    public ResponseEntity<TimetableEntryResponse> createTimetableEntry(
            @Valid @RequestBody TimetableEntryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timetableEntryService.createTimetableEntry(request));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TimetableEntryResponse>> getTimetableByStudent(
            @PathVariable Long studentId,
            Authentication authentication
    ) {
        studentAccessService.validateStudentAccess(authentication.getName(), studentId);
        return ResponseEntity.ok(timetableEntryService.getTimetableByStudent(studentId));
    }
}