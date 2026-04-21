import { Link } from "react-router-dom";

function HomePage() {
  return (
    <div className="page-section">
      <div className="grid-two">
        <div className="card">
          <h1>Student Dashboard</h1>
          <p className="subtitle">
            View grades, analytics, timetable and exams.
          </p>

          <Link to="/students/1/dashboard" className="button-link">
            Open Student Area
          </Link>
        </div>

        <div className="card">
          <h1>Teacher Grade Entry</h1>
          <p className="subtitle">
            Create a new grade for a student.
          </p>

          <Link to="/teacher/grades/new" className="button-link">
            Open Grade Form
          </Link>
        </div>
      </div>
    </div>
  );
}

export default HomePage;