package ma.local.school_management_app.academic.repository;

import ma.local.school_management_app.academic.entity.TimetableEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, Long> {
    List<TimetableEntry> findByStudentId(Long studentId);
}