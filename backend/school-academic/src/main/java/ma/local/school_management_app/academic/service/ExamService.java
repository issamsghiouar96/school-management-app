package ma.local.school_management_app.academic.service;

import ma.local.school_management_app.academic.dto.ExamRequest;
import ma.local.school_management_app.academic.dto.ExamResponse;
import ma.local.school_management_app.academic.entity.Exam;
import ma.local.school_management_app.academic.entity.Subject;
import ma.local.school_management_app.academic.repository.ExamRepository;
import ma.local.school_management_app.academic.repository.SubjectRepository;
import ma.local.school_management_app.common.exception.ResourceNotFoundException;
import ma.local.school_management_app.user.entity.Student;
import ma.local.school_management_app.user.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public ExamService(
            ExamRepository examRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository
    ) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public ExamResponse createExam(ExamRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Exam exam = new Exam();
        exam.setExamDate(LocalDate.parse(request.getExamDate()));
        exam.setType(request.getType());
        exam.setDescription(request.getDescription());
        exam.setStudent(student);
        exam.setSubject(subject);

        return mapToResponse(examRepository.save(exam));
    }

    public List<ExamResponse> getExamsByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }

        return examRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExamResponse mapToResponse(Exam exam) {
        ExamResponse response = new ExamResponse();
        response.setId(exam.getId());
        response.setExamDate(exam.getExamDate().toString());
        response.setType(exam.getType());
        response.setDescription(exam.getDescription());

        response.setStudentId(exam.getStudent().getId());
        response.setStudentCode(exam.getStudent().getStudentCode());
        response.setStudentFullName(
                exam.getStudent().getUser().getFirstName() + " " +
                exam.getStudent().getUser().getLastName()
        );

        response.setSubjectId(exam.getSubject().getId());
        response.setSubjectName(exam.getSubject().getName());

        return response;
    }
}