package ma.local.school_management_app.academic.service;

import ma.local.school_management_app.academic.dto.GradeRequest;
import ma.local.school_management_app.academic.dto.GradeResponse;
import ma.local.school_management_app.academic.entity.Grade;
import ma.local.school_management_app.academic.entity.Subject;
import ma.local.school_management_app.academic.repository.GradeRepository;
import ma.local.school_management_app.academic.repository.SubjectRepository;
import ma.local.school_management_app.user.entity.Student;
import ma.local.school_management_app.user.entity.Teacher;
import ma.local.school_management_app.user.repository.StudentRepository;
import ma.local.school_management_app.user.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public GradeService(
            GradeRepository gradeRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository
    ) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public GradeResponse createGrade(GradeRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Grade grade = new Grade();
        grade.setValue(request.getValue());
        grade.setSemester(request.getSemester());
        grade.setStudent(student);
        grade.setTeacher(teacher);
        grade.setSubject(subject);

        Grade savedGrade = gradeRepository.save(grade);
        return mapToResponse(savedGrade);
    }

    public List<GradeResponse> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private GradeResponse mapToResponse(Grade grade) {
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
}