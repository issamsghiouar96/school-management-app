import api from "./axios";
import type { ExamResponse } from "../types/exam";

export const getStudentExams = (studentId: string) =>
  api.get<ExamResponse[]>(`/exams/student/${studentId}`);