export interface TimetableEntryResponse {
  id: number;
  dayOfWeek: string;
  startTime: string;
  endTime: string;
  room: string;
  studentId: number;
  studentCode: string;
  studentFullName: string;
  teacherId: number;
  teacherFullName: string;
  subjectId: number;
  subjectName: string;
}