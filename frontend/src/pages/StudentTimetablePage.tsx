import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getStudentTimetable } from "../api/timetableApi";
import type { TimetableEntryResponse } from "../types/timetable";
import StudentTabs from "../components/StudentTabs";
import TimetableTable from "../components/student/TimetableTable";
import StudentSelector from "../components/student/StudentSelector";
import { useAuth } from "../context/AuthContext";

function StudentTimetablePage() {
  const { studentId } = useParams<{ studentId: string }>();

  const [entries, setEntries] = useState<TimetableEntryResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const { user } = useAuth();
  useEffect(() => {
    if (!studentId) return;

    const loadTimetable = async () => {
      try {
        setLoading(true);
        setError("");
        const response = await getStudentTimetable(studentId);
        setEntries(response.data);
      } catch {
        setError("Failed to load timetable");
      } finally {
        setLoading(false);
      }
    };

    loadTimetable();
  }, [studentId]);

  if (loading) return <p>Loading timetable...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!studentId) return <p>No student selected.</p>;

  return (
    <div className="page-section">
      <div className="page-header">
        <h1>Student Timetable</h1>
        <p className="subtitle">Weekly class schedule</p>
      </div>
      {user?.role !== "STUDENT" && <StudentSelector />}
      <StudentTabs studentId={studentId} />
      <TimetableTable entries={entries} />
    </div>
  );
}

export default StudentTimetablePage;