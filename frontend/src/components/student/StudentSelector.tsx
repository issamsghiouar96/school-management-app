import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getAllStudents } from "../../api/studentListApi";
import type { Student } from "../../types/student";
import SearchableSelect, { type SearchableOption, } from "../SearchableSelect";

function StudentSelector() {
  const navigate = useNavigate();
  const { studentId } = useParams<{ studentId: string }>();

  const [students, setStudents] = useState<Student[]>([]);
  const [selectedId, setSelectedId] = useState<number | null>(null);

  useEffect(() => {
    const loadStudents = async () => {
      try {
        const res = await getAllStudents();
        setStudents(res.data);
      } catch (err) {
        console.error("Failed to load students", err);
      }
    };
    loadStudents();
  }, []);

  useEffect(() => {
    if (studentId) {
      setSelectedId(Number(studentId));
    }
  }, [studentId]);

  /*const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newId = Number(e.target.value);
    setSelectedId(newId);

    navigate(`/students/${newId}/dashboard`);
  };*/
  const studentOptions: SearchableOption[] = students.map((student) => ({
    value: student.id,
    label: `${student.user.firstName} ${student.user.lastName}`,
  }));

  return (
      <div className="student-selector">
        <SearchableSelect
          label="Select Student"
          options={studentOptions}
          value={selectedId ?? ""}
          onChange={(value) => {
            const newId = Number(value);
            setSelectedId(newId);

            const currentPath = window.location.pathname.split("/")[3] || "dashboard";
            navigate(`/students/${newId}/${currentPath}`);
          }}
          placeholder="Search student..."
        />
      </div>
  );
}

export default StudentSelector;