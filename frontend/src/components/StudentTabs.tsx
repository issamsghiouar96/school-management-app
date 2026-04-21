import { NavLink } from "react-router-dom";

interface Props {
  studentId: string;
}

function StudentTabs({ studentId }: Props) {
  return (
    <div className="student-tabs">
      <NavLink
        to={`/students/${studentId}/dashboard`}
        className={({ isActive }) =>
          isActive ? "student-tab active" : "student-tab"
        }
      >
        Dashboard
      </NavLink>

      <NavLink
        to={`/students/${studentId}/timetable`}
        className={({ isActive }) =>
          isActive ? "student-tab active" : "student-tab"
        }
      >
        Timetable
      </NavLink>

      <NavLink
        to={`/students/${studentId}/exams`}
        className={({ isActive }) =>
          isActive ? "student-tab active" : "student-tab"
        }
      >
        Exams
      </NavLink>
    </div>
  );
}

export default StudentTabs;