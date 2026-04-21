package ma.local.school_management_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ActiveProfiles("test")
public class SchoolManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolManagementAppApplication.class, args);
	}

}
