package ma.local.school_management_app.user.repository;

import ma.local.school_management_app.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}