import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getStudentExams } from "../api/examApi";
import type { ExamResponse } from "../types/exam";
import StudentTabs from "../components/StudentTabs";
import ExamsTable from "../components/student/ExamsTable";
import StudentSelector from "../components/student/StudentSelector";
import { useAuth } from "../context/AuthContext";

function StudentExamsPage() {
  const { studentId } = useParams<{ studentId: string }>();
  const [exams, setExams] = useState<ExamResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const { user } = useAuth();

  useEffect(() => {
    if (!studentId) return;

    const loadExams = async () => {
      try {
        setLoading(true);
        setError("");
        const response = await getStudentExams(studentId);
        setExams(response.data);
      } catch {
        setError("Failed to load exams");
      } finally {
        setLoading(false);
      }
    };

    loadExams();
  }, [studentId]);

  if (loading) return <p>Loading exams...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!studentId) return <p>No student selected.</p>;

  return (
    <div className="page-section">
      <div className="page-header">
        <h1>Student Exams</h1>
        <p className="subtitle">Scheduled exams and assessments</p>
      </div>
      {user?.role !== "STUDENT" && <StudentSelector />}
      <StudentTabs studentId={studentId} />
      <ExamsTable exams={exams} />
    </div>
  );
}

export default StudentExamsPage;