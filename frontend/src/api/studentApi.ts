import api from "./axios";
import type { GradeResponse } from "../types/grade";
import type { StudentAnalyticsResponse } from "../types/analytics";

export const getStudentGrades = (studentId: string) =>
  api.get<GradeResponse[]>(`/students/${studentId}/grades`);

export const getStudentAnalytics = (studentId: string) =>
  api.get<StudentAnalyticsResponse>(`/students/${studentId}/analytics`);