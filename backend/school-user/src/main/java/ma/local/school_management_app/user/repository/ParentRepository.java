package ma.local.school_management_app.user.repository;

import ma.local.school_management_app.user.entity.Parent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
      Optional<Parent> findByUserId(Long userId);
}