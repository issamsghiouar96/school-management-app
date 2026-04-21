import api from "./axios";
import type { ParentChild } from "../types/parent";

export const getMyChildren = () =>
  api.get<ParentChild[]>("/parents/me/children");

export const getChildGrades = (studentId: number) =>
  api.get(`/parents/me/children/${studentId}/grades`);

export const getChildAnalytics = (studentId: number) =>
  api.get(`/parents/me/children/${studentId}/analytics`);

export const getChildTimetable = (studentId: number) =>
  api.get(`/parents/me/children/${studentId}/timetable`);

export const getChildExams = (studentId: number) =>
  api.get(`/parents/me/children/${studentId}/exams`);