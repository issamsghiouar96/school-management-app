package ma.local.school_management_app.user.repository;

import ma.local.school_management_app.user.entity.ParentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {

    List<ParentStudent> findByParentId(Long parentId);

    boolean existsByParentIdAndStudentId(Long parentId, Long studentId);

    Optional<ParentStudent> findByParentIdAndStudentId(Long parentId, Long studentId);
}