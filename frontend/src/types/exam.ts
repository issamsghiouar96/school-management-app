export interface ExamResponse {
  id: number;
  examDate: string;
  type: string;
  description: string;
  studentId: number;
  studentCode: string;
  studentFullName: string;
  subjectId?: number;
  subjectName: string;
}