import api from "./axios";
import type { Teacher } from "../types/teacher";

export const getAllTeachers = () => api.get<Teacher[]>("/teachers");