import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { createGrade } from "../api/gradeApi";
import { getAllStudents } from "../api/studentListApi";
import { getAllTeachers } from "../api/teacherApi";
import { getAllSubjects } from "../api/subjectApi";
import type { Student } from "../types/student";
import type { Teacher } from "../types/teacher";
import type { Subject } from "../types/subject";
import type { GradeRequest } from "../types/gradeRequest";
import SearchableSelect, {
    type SearchableOption,
} from "../components/SearchableSelect";
import PageLoader from "../components/common/PageLoader";

function TeacherGradePage() {
    const navigate = useNavigate();

    const [students, setStudents] = useState<Student[]>([]);
    const [teachers, setTeachers] = useState<Teacher[]>([]);
    const [subjects, setSubjects] = useState<Subject[]>([]);

    const [form, setForm] = useState<GradeRequest>({
        value: 0,
        semester: "S1",
        studentId: 0,
        teacherId: 0,
        subjectId: 0,
    });

    const [loading, setLoading] = useState(false);
    const [pageLoading, setPageLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadData = async () => {
            try {
                setPageLoading(true);
                setError("");

                const [studentsRes, teachersRes, subjectsRes] = await Promise.all([
                    getAllStudents(),
                    getAllTeachers(),
                    getAllSubjects(),
                ]);

                setStudents(studentsRes.data);
                setTeachers(teachersRes.data);
                setSubjects(subjectsRes.data);

                setForm((prev) => ({
                    ...prev,
                    studentId: studentsRes.data[0]?.id ?? 0,
                    teacherId: teachersRes.data[0]?.id ?? 0,
                    subjectId: subjectsRes.data[0]?.id ?? 0,
                }));
            } catch (err) {
                console.error(err);
                setError("Failed to load form data");
            } finally {
                setPageLoading(false);
            }
        };

        loadData();
    }, []);

    /*const handleChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) => {
        const { name, value } = e.target;

        setForm((prev) => ({
            ...prev,
            [name]:
                name === "value" || name === "studentId" || name === "teacherId" || name === "subjectId"
                    ? Number(value)
                    : value,
        }));
    };*/

    const studentOptions: SearchableOption[] = students.map((student) => ({
        value: student.id,
        label: `${student.user.firstName} ${student.user.lastName}`,
    }));

    const teacherOptions: SearchableOption[] = teachers.map((teacher) => ({
        value: teacher.id,
        label: `${teacher.user.firstName} ${teacher.user.lastName}`,
    }));

    const subjectOptions: SearchableOption[] = subjects.map((subject) => ({
        value: subject.id,
        label: subject.name,
    }));

    const semesterOptions: SearchableOption[] = [
        { value: "S1", label: "S1" },
        { value: "S2", label: "S2" },
    ];

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        try {
            setLoading(true);
            await createGrade(form);

            toast.success("Grade created successfully");

            const selectedStudentId = form.studentId;

            setForm((prev) => ({
                ...prev,
                value: 0,
                semester: "S1",
            }));

            setTimeout(() => {
                navigate(`/students/${selectedStudentId}/dashboard`);
            }, 800);
        } catch (err: any) {
            const message = err?.response?.data?.message || "Failed to create grade";
            setError(message);
            toast.error(message);
        } finally {
            setLoading(false);
        }
    };

    if (pageLoading) return <PageLoader/>;

    return (
        <div className="page-section">
            <div className="page-header">
                <h1>Teacher Grade Entry</h1>
                <p className="subtitle">Create a new grade for a student.</p>
            </div>

            <div className="card form-card">
                <form className="form-grid" onSubmit={handleSubmit}>
                    <SearchableSelect
                        label="Student"
                        options={studentOptions}
                        value={form.studentId}
                        onChange={(selectedValue) =>
                            setForm((prev) => ({ ...prev, studentId: selectedValue as number }))
                        }
                        placeholder="Search student..."
                    />

                    <SearchableSelect
                        label="Teacher"
                        options={teacherOptions}
                        value={form.teacherId}
                        onChange={(selectedValue) =>
                            setForm((prev) => ({ ...prev, teacherId: selectedValue as number }))
                        }
                        placeholder="Search teacher..."
                    />

                    <SearchableSelect
                        label="Subject"
                        options={subjectOptions}
                        value={form.subjectId}
                        onChange={(selectedValue) =>
                            setForm((prev) => ({ ...prev, subjectId: selectedValue as number }))
                        }
                        placeholder="Search subject..."
                    />

                    <SearchableSelect
                        label="Semester"
                        options={semesterOptions}
                        value={form.semester}
                        onChange={(selectedValue) =>
                            setForm((prev) => ({
                                ...prev,
                                semester: selectedValue as string,
                            }))
                        }
                    />

                    <div className="form-group">
                        <label htmlFor="value">Grade</label>
                        <input
                            id="value"
                            name="value"
                            type="text"
                            placeholder="Enter grade (0 - 20)"
                            value={form.value === 0 ? "" : form.value}
                            onChange={(e) => {
                                const input = e.target.value;

                                // allow empty input
                                if (input === "") {
                                    setForm((prev) => ({ ...prev, value: 0 }));
                                    return;
                                }

                                // allow only numbers + decimal
                                if (!/^\d*\.?\d*$/.test(input)) return;

                                const numericValue = Number(input);

                                // limit range
                                if (numericValue > 20) return;

                                setForm((prev) => ({
                                    ...prev,
                                    value: numericValue,
                                }));
                            }}
                        />
                    </div>

                    <div className="form-actions">
                        <button type="submit" disabled={loading}>
                            {loading ? "Saving..." : "Create Grade"}
                        </button>
                    </div>
                </form>

                {error && <p className="error">{error}</p>}
            </div>
        </div>
    );
}

export default TeacherGradePage;