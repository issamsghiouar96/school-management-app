export interface SubjectAverageResponse {
  subjectName: string;
  average: number;
}

export interface StudentAnalyticsResponse {
  studentId: number;
  studentCode: string;
  studentFullName: string;
  overallAverage: number;
  highestGrade: number;
  lowestGrade: number;
  totalGrades: number;
  subjectAverages: SubjectAverageResponse[];
}