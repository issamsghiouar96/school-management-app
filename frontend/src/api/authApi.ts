import api from "./axios";
import type { LoginRequest, LoginResponse } from "../types/auth";

export const loginUser = (payload: LoginRequest) => 
    api.post<LoginResponse>("/auth/login", payload);