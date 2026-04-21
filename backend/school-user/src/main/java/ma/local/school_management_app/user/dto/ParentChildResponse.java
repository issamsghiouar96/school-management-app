package ma.local.school_management_app.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentChildResponse {

    private Long studentId;
    private String studentCode;
    private String className;
    private String firstName;
    private String lastName;
}