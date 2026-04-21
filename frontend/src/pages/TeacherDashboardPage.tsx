import { Link } from "react-router-dom";

function TeacherDashboardPage() {
   console.log("TeacherDashboardPage rendered");
  return (
    <div className="page-section">
      <div className="page-header">
        <h1>Teacher Dashboard</h1>
        <p className="subtitle">
          Access student dashboards and manage grades.
        </p>
      </div>

      <div className="grid-two">
        <div className="card">
          <h2>Student Area</h2>
          <p>Open a student dashboard to view grades, timetable and exams.</p>
          <Link to="/students/1/dashboard" className="button-link">
            Open Student Dashboard
          </Link>
        </div>

        <div className="card">
          <h2>Grade Entry</h2>
          <p>Create a new grade for a student.</p>
          <Link to="/teacher/grades/new" className="button-link">
            Open Grade Form
          </Link>
        </div>
      </div>
    </div>
  );
}

export default TeacherDashboardPage;