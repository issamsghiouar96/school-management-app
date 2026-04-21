import api from "./axios";
import type { Student } from "../types/student";

export const getAllStudents = () => api.get<Student[]>("/students");