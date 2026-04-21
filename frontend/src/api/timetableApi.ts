import api from "./axios";
import type { TimetableEntryResponse } from "../types/timetable";

export const getStudentTimetable = (studentId: string) =>
  api.get<TimetableEntryResponse[]>(`/timetable/student/${studentId}`);