import { useEffect, useState } from "react";
import {
  getMyChildren,
  getChildGrades,
  getChildAnalytics,
  getChildTimetable,
  getChildExams,
} from "../api/parentApi";
import type { ParentChild } from "../types/parent";
import type { GradeResponse } from "../types/grade";
import type { StudentAnalyticsResponse } from "../types/analytics";
import type { TimetableEntryResponse } from "../types/timetable";
import type { ExamResponse } from "../types/exam";
import SearchableSelect, {
  type SearchableOption,
} from "../components/SearchableSelect";
import AnalyticsCards from "../components/student/AnalyticsCards";
import SubjectAveragesChart from "../components/student/SubjectAveragesChart";
import GradesTable from "../components/student/GradesTable";
import TimetableTable from "../components/student/TimetableTable";
import ExamsTable from "../components/student/ExamsTable";

function ParentDashboardPage() {
  const [children, setChildren] = useState<ParentChild[]>([]);
  const [selectedId, setSelectedId] = useState<number | null>(null);

  const [grades, setGrades] = useState<GradeResponse[]>([]);
  const [analytics, setAnalytics] = useState<StudentAnalyticsResponse | null>(null);
  const [timetable, setTimetable] = useState<TimetableEntryResponse[]>([]);
  const [exams, setExams] = useState<ExamResponse[]>([]);

  const [loading, setLoading] = useState(true);
  const [childLoading, setChildLoading] = useState(false);
  const [error, setError] = useState("");

  // Load children
  useEffect(() => {
    const loadChildren = async () => {
      try {
        setLoading(true);
        const res = await getMyChildren();
        setChildren(res.data);

        if (res.data.length > 0) {
          setSelectedId(res.data[0].studentId);
        }
      } catch {
        setError("Failed to load children");
      } finally {
        setLoading(false);
      }
    };

    loadChildren();
  }, []);

  useEffect(() => {
    if (!selectedId) return;

    const loadChildData = async () => {
      try {
        setChildLoading(true);

        const [gradesRes, analyticsRes, timetableRes, examsRes] =
          await Promise.all([
            getChildGrades(selectedId),
            getChildAnalytics(selectedId),
            getChildTimetable(selectedId),
            getChildExams(selectedId),
          ]);

        setGrades(gradesRes.data);
        setAnalytics(analyticsRes.data);
        setTimetable(timetableRes.data);
        setExams(examsRes.data);
      } catch {
        setError("Failed to load child data");
      } finally {
        setChildLoading(false);
      }
    };

    loadChildData();
  }, [selectedId]);

  const options: SearchableOption[] = children.map((c) => ({
    value: c.studentId,
    label: `${c.firstName} ${c.lastName}`,
  }));

  if (loading) return <p>Loading parent dashboard...</p>;
  if (error) return <p className="error">{error}</p>;

  return (
    <div className="page-section">
      <div className="page-header">
        <h1>Parent Dashboard</h1>
        <p className="subtitle">View your child's academic data</p>
      </div>

      <SearchableSelect
        label="Select Child"
        options={options}
        value={selectedId ?? ""}
        onChange={(val) => setSelectedId(Number(val))}
        placeholder="Search child..."
      />

      {childLoading && <p>Loading data...</p>}

      {!childLoading && analytics && (
        <>
          <h2>
            {analytics.studentFullName} ({analytics.studentCode})
          </h2>

          <AnalyticsCards analytics={analytics} />
          <SubjectAveragesChart data={analytics.subjectAverages} />
          <GradesTable grades={grades} />
          <TimetableTable entries={timetable} />
          <ExamsTable exams={exams} />
        </>
      )}
    </div>
  );
}

export default ParentDashboardPage;