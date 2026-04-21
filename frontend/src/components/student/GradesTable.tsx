import type { GradeResponse } from "../../types/grade";

interface Props {
  grades: GradeResponse[];
}

function GradesTable({ grades }: Props) {
  return (
    <div className="card">
      <h2>Grades</h2>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Subject</th>
              <th>Grade</th>
              <th>Semester</th>
              <th>Teacher</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {grades.map((grade) => (
              <tr key={grade.id}>
                <td>{grade.subjectName}</td>
                <td>{grade.value}</td>
                <td>{grade.semester}</td>
                <td>{grade.teacherFullName}</td>
                <td>{new Date(grade.createdAt).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default GradesTable;