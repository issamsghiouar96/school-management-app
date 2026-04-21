export interface GradeResponse {
  id: number;
  value: number;
  semester: string;
  createdAt: string;
  studentId: number;
  studentCode: string;
  studentFullName: string;
  teacherId: number;
  teacherFullName: string;
  subjectId: number;
  subjectName: string;
}