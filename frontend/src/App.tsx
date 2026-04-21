import { Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { Navigate } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import StudentDashboardPage from "./pages/StudentDashboardPage";
import StudentTimetablePage from "./pages/StudentTimetablePage";
import StudentExamsPage from "./pages/StudentExamsPage";
import TeacherGradePage from "./pages/TeacherGradePage";
import ProtectedRoute from "./components/auth/ProtectedRoute";
import TeacherDashboardPage from "./pages/TeacherDashboardPage";
import ParentDashboardPage from "./pages/ParentDashboardPage";

function App() {
  return (
    <>
      <Navbar />

      <main className="container">
        <Routes>
          <Route path="/login" element={<LoginPage />} />

          <Route path="/" element={<Navigate to="/login" replace />} />

          <Route
            path="/students/:studentId/dashboard"
            element={
              <ProtectedRoute allowedRoles={["STUDENT", "TEACHER", "PARENT", "ADMIN"]}>
                <StudentDashboardPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/students/:studentId/timetable"
            element={
              <ProtectedRoute allowedRoles={["STUDENT", "TEACHER", "PARENT", "ADMIN"]}>
                <StudentTimetablePage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/students/:studentId/exams"
            element={
              <ProtectedRoute allowedRoles={["STUDENT", "TEACHER", "PARENT", "ADMIN"]}>
                <StudentExamsPage />
              </ProtectedRoute>
            }
          />

          <Route
            path="/teacher/grades/new"
            element={
              <ProtectedRoute allowedRoles={["TEACHER", "ADMIN"]}>
                <TeacherGradePage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/teacher/dashboard"
            element={
              <ProtectedRoute allowedRoles={["TEACHER", "ADMIN"]}>
                <TeacherDashboardPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/parent/dashboard"
            element={
              <ProtectedRoute allowedRoles={["PARENT", "ADMIN"]}>
                <ParentDashboardPage />
              </ProtectedRoute>
            }
          />
        </Routes>
      </main>

      <ToastContainer position="top-right" autoClose={2500} />
    </>
  );
}

export default App;