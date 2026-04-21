import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getStudentAnalytics, getStudentGrades } from "../api/studentApi";
import type { GradeResponse } from "../types/grade";
import type { StudentAnalyticsResponse } from "../types/analytics";
import AnalyticsCards from "../components/student/AnalyticsCards";
import GradesTable from "../components/student/GradesTable";
import SubjectAveragesChart from "../components/student/SubjectAveragesChart";
import StudentTabs from "../components/StudentTabs";
import PageLoader from "../components/common/PageLoader";
import StudentSelector from "../components/student/StudentSelector";
import { useAuth } from "../context/AuthContext";

function StudentDashboardPage() {
  const { studentId } = useParams<{ studentId: string }>();

  const [grades, setGrades] = useState<GradeResponse[]>([]);
  const [analytics, setAnalytics] = useState<StudentAnalyticsResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const { user } = useAuth();

  useEffect(() => {
    if (!studentId) return;

    const loadDashboard = async () => {
      try {
        setLoading(true);
        setError("");

        const [gradesResponse, analyticsResponse] = await Promise.all([
          getStudentGrades(studentId),
          getStudentAnalytics(studentId),
        ]);

        setGrades(gradesResponse.data);
        setAnalytics(analyticsResponse.data);
      } catch {
        setError("Failed to load student dashboard");
      } finally {
        setLoading(false);
      }
    };

    loadDashboard();
  }, [studentId]);

  if (loading) return <PageLoader/>;
  if (error) return <p className="error">{error}</p>;
  if (!analytics || !studentId) return <p>No analytics found.</p>;

  return (
    <div className="page-section">
      <div className="page-header">
        <div className="header-row">
        <h1>{analytics.studentFullName}</h1>
        <p className="subtitle">Student Code: {analytics.studentCode}</p>
        </div>
      </div>

      {user?.role !== "STUDENT" && <StudentSelector />}
      <StudentTabs studentId={studentId} />
      <AnalyticsCards analytics={analytics} />
      <SubjectAveragesChart data={analytics.subjectAverages} />
      <GradesTable grades={grades} />
    </div>
  );
}

export default StudentDashboardPage;