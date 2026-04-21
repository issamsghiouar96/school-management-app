import {
  ResponsiveContainer,
  BarChart,
  Bar,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Cell,
} from "recharts";
import type { SubjectAverageResponse } from "../../types/analytics";

interface Props {
  data: SubjectAverageResponse[];
}

function SubjectAveragesChart({ data }: Props) {
  const getBarColor = (average: number) => {
    if (average >= 16) return "#22c55e";
    if (average >= 10) return "#fbbf24";
    return "#f87171";
  };

  return (
    <div className="card">
      <h2>Subject Averages</h2>

      <div className="chart-box">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="subjectName" tick={{ fill: "#6b7280", fontSize: 14 }} />
            <YAxis domain={[0, 20]} tick={{ fill: "#6b7280", fontSize: 14 }} />
            <Tooltip
              contentStyle={{
                borderRadius: "10px",
                border: "1px solid #e5e7eb",
                backgroundColor: "#ffffff",
              }}
            />
            <Bar dataKey="average" radius={[8, 8, 0, 0]}>
              {data.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={getBarColor(entry.average)} />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}

export default SubjectAveragesChart;