package ma.local.school_management_app.academic.service;

import ma.local.school_management_app.academic.dto.TimetableEntryRequest;
import ma.local.school_management_app.academic.dto.TimetableEntryResponse;
import ma.local.school_management_app.academic.entity.Subject;
import ma.local.school_management_app.academic.entity.TimetableEntry;
import ma.local.school_management_app.academic.repository.SubjectRepository;
import ma.local.school_management_app.academic.repository.TimetableEntryRepository;
import ma.local.school_management_app.common.exception.ResourceNotFoundException;
import ma.local.school_management_app.user.entity.Student;
import ma.local.school_management_app.user.entity.Teacher;
import ma.local.school_management_app.user.repository.StudentRepository;
import ma.local.school_management_app.user.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
public class TimetableEntryService {

    private final TimetableEntryRepository timetableEntryRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TimetableEntryService(
            TimetableEntryRepository timetableEntryRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository
    ) {
        this.timetableEntryRepository = timetableEntryRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public TimetableEntryResponse createTimetableEntry(TimetableEntryRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        TimetableEntry entry = new TimetableEntry();
        entry.setDayOfWeek(request.getDayOfWeek());
        entry.setStartTime(LocalTime.parse(request.getStartTime()));
        entry.setEndTime(LocalTime.parse(request.getEndTime()));
        entry.setRoom(request.getRoom());
        entry.setStudent(student);
        entry.setTeacher(teacher);
        entry.setSubject(subject);

        return mapToResponse(timetableEntryRepository.save(entry));
    }

    public List<TimetableEntryResponse> getTimetableByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }

        return timetableEntryRepository.findByStudentId(studentId)
                .stream()
                .sorted(Comparator
                        .comparing(TimetableEntry::getDayOfWeek)
                        .thenComparing(TimetableEntry::getStartTime))
                .map(this::mapToResponse)
                .toList();
    }

    private TimetableEntryResponse mapToResponse(TimetableEntry entry) {
        TimetableEntryResponse response = new TimetableEntryResponse();
        response.setId(entry.getId());
        response.setDayOfWeek(entry.getDayOfWeek());
        response.setStartTime(entry.getStartTime().toString());
        response.setEndTime(entry.getEndTime().toString());
        response.setRoom(entry.getRoom());

        response.setStudentId(entry.getStudent().getId());
        response.setStudentCode(entry.getStudent().getStudentCode());
        response.setStudentFullName(
                entry.getStudent().getUser().getFirstName() + " " +
                entry.getStudent().getUser().getLastName()
        );

        response.setTeacherId(entry.getTeacher().getId());
        response.setTeacherFullName(
                entry.getTeacher().getUser().getFirstName() + " " +
                entry.getTeacher().getUser().getLastName()
        );

        response.setSubjectId(entry.getSubject().getId());
        response.setSubjectName(entry.getSubject().getName());

        return response;
    }
}