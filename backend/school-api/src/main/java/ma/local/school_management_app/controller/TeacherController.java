package ma.local.school_management_app.controller;

import ma.local.school_management_app.user.entity.Teacher;
import ma.local.school_management_app.user.repository.TeacherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }
}