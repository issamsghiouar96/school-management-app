package ma.local.school_management_app.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "parent_student",
    uniqueConstraints = @UniqueConstraint(columnNames = {"parent_id", "student_id"})
)
public class ParentStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;
}