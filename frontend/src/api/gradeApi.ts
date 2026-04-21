import api from "./axios";
import type { GradeRequest } from "../types/gradeRequest";
import type { GradeResponse } from "../types/grade";

export const createGrade = (payload: GradeRequest) =>
  api.post<GradeResponse>("/grades", payload);