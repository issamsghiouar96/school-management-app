import api from "./axios";
import type { Subject } from "../types/subject";

export const getAllSubjects = () => api.get<Subject[]>("/subjects");