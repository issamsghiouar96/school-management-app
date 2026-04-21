export interface Student {
  id: number;
  studentCode: string;
  className: string;
  user: {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    role: string;
  };
}