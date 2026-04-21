package ma.local.school_management_app.academic.repository;

import ma.local.school_management_app.academic.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}