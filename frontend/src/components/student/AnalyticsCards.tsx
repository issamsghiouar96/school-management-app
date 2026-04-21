import type { StudentAnalyticsResponse } from "../../types/analytics";

interface Props {
  analytics: StudentAnalyticsResponse;
}

function AnalyticsCards({ analytics }: Props) {
  return (
    <div className="cards-grid">
      <div className="card stat-card stat-blue">
        <h3>Overall Average</h3>
        <p>{analytics.overallAverage}</p>
      </div>

      <div className="card stat-card stat-green">
        <h3>Highest Grade</h3>
        <p>{analytics.highestGrade}</p>
      </div>

      <div className="card stat-card stat-red">
        <h3>Lowest Grade</h3>
        <p>{analytics.lowestGrade}</p>
      </div>

      <div className="card stat-card stat-purple">
        <h3>Total Grades</h3>
        <p>{analytics.totalGrades}</p>
      </div>
    </div>
  );
}

export default AnalyticsCards;