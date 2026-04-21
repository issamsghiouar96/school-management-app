import type { ExamResponse } from "../../types/exam";

interface Props {
  exams: ExamResponse[];
}

function ExamsTable({ exams }: Props) {
  return (
    <div className="card">
      <h2>Upcoming Exams</h2>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Subject</th>
              <th>Type</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
            {exams.map((exam) => (
              <tr key={exam.id}>
                <td>{new Date(exam.examDate).toLocaleDateString()}</td>
                <td>{exam.subjectName}</td>
                <td>
                  <span className={`exam-badge exam-${exam.type.toLowerCase()}`}>
                    {exam.type}
                  </span>
                </td>
                <td>{exam.description}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ExamsTable;