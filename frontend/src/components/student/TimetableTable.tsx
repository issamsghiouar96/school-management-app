import type { TimetableEntryResponse } from "../../types/timetable";

interface Props {
  entries: TimetableEntryResponse[];
}

function TimetableTable({ entries }: Props) {
  return (
    <div className="card">
      <h2>Timetable</h2>

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Day</th>
              <th>Start</th>
              <th>End</th>
              <th>Subject</th>
              <th>Teacher</th>
              <th>Room</th>
            </tr>
          </thead>
          <tbody>
            {entries.map((entry) => (
              <tr key={entry.id}>
                <td>{entry.dayOfWeek}</td>
                <td>{entry.startTime}</td>
                <td>{entry.endTime}</td>
                <td>{entry.subjectName}</td>
                <td>{entry.teacherFullName}</td>
                <td>{entry.room}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default TimetableTable;