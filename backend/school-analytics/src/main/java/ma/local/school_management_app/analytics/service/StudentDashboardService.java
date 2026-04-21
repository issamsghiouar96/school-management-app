package ma.local.school_management_app.analytics.service;

import ma.local.school_management_app.academic.dto.GradeResponse;
import ma.local.school_management_app.academic.entity.Grade;
import ma.local.school_management_app.academic.repository.GradeRepository;
import ma.local.school_management_app.analytics.dto.StudentAnalyticsResponse;
import ma.local.school_management_app.analytics.dto.SubjectAverageResponse;
import ma.local.school_management_app.user.entity.Student;
import ma.local.school_management_app.user.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentDashboardService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    public StudentDashboardService(GradeRepository gradeRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
    }

    public List<GradeResponse> getStudentGrades(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return gradeRepository.findByStudentId(student.getId())
                .stream()
                .map(this::mapToGradeResponse)
                .toList();
    }

    public StudentAnalyticsResponse getStudentAnalytics(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Grade> grades = gradeRepository.findByStudentId(studentId);

        StudentAnalyticsResponse response = new StudentAnalyticsResponse();
        response.setStudentId(student.getId());
        response.setStudentCode(student.getStudentCode());
        response.setStudentFullName(
                student.getUser().getFirstName() + " " + student.getUser().getLastName()
        );
        response.setTotalGrades(grades.size());

        if (grades.isEmpty()) {
            response.setOverallAverage(0.0);
            response.setHighestGrade(0.0);
            response.setLowestGrade(0.0);
            response.setSubjectAverages(List.of());
            return response;
        }

        double overallAverage = grades.stream()
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0.0);

        double highestGrade = grades.stream()
                .map(Grade::getValue)
                .max(Comparator.naturalOrder())
                .orElse(0.0);

        double lowestGrade = grades.stream()
                .map(Grade::getValue)
                .min(Comparator.naturalOrder())
                .orElse(0.0);

        Map<String, Double> averagesBySubject = grades.stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getSubject().getName(),
                        Collectors.averagingDouble(Grade::getValue)
                ));

        List<SubjectAverageResponse> subjectAverages = averagesBySubject.entrySet()
                .stream()
                .map(entry -> new SubjectAverageResponse(entry.getKey(), entry.getValue()))
                .toList();

        response.setOverallAverage(round(overallAverage));
        response.setHighestGrade(round(highestGrade));
        response.setLowestGrade(round(lowestGrade));
        response.setSubjectAverages(subjectAverages);

        return response;
    }

    private GradeResponse mapToGradeResponse(Grade grade) {
        GradeResponse response = new GradeResponse();
        response.setId(grade.getId());
        response.setValue(grade.getValue());
        response.setSemester(grade.getSemester());
        response.setCreatedAt(grade.getCreatedAt());

        response.setStudentId(grade.getStudent().getId());
        response.setStudentCode(grade.getStudent().getStudentCode());
        response.setStudentFullName(
                grade.getStudent().getUser().getFirstName() + " " + grade.getStudent().getUser().getLastName()
        );

        response.setTeacherId(grade.getTeacher().getId());
        response.setTeacherFullName(
                grade.getTeacher().getUser().getFirstName() + " " + grade.getTeacher().getUser().getLastName()
        );

        response.setSubjectId(grade.getSubject().getId());
        response.setSubjectName(grade.getSubject().getName());

        return response;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}