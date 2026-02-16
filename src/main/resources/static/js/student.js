const STUDENT_API = "http://localhost:8080/api/students";
const COURSE_API = "http://localhost:8080/api/courses";

document.addEventListener("DOMContentLoaded", () => {
    fetchStudents();
    loadCourseCheckboxes();
});

/* -------- STUDENTS -------- */

function fetchStudents() {
    fetch(STUDENT_API)
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("studentTable");
            table.innerHTML = "";

            data.forEach(s => {
                table.innerHTML += `
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.name}</td>
                        <td>${s.department}</td>
                        <td>${s.email}</td>
                        <td>${(s.courseIds || []).join(", ")}</td>
                        <td>
                            <button class="btn btn-sm btn-warning"
                                onclick="editStudent(${s.id})">Edit</button>
                            <button class="btn btn-sm btn-danger"
                                onclick="deleteStudent(${s.id})">Delete</button>
                        </td>
                    </tr>
                `;
            });
        });
}

function saveStudent() {
    const id = document.getElementById("studentId").value;

    const courseIds = [...document.querySelectorAll("#courseCheckboxes input:checked")]
        .map(cb => Number(cb.value));

    const student = {
        name: document.getElementById("name").value,
        department: document.getElementById("department").value,
        email: document.getElementById("email").value,
        courseIds
    };

    const method = id ? "PUT" : "POST";
    const url = id ? `${STUDENT_API}/${id}` : STUDENT_API;

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(student)
    }).then(() => {
        bootstrap.Modal.getInstance(
            document.getElementById("studentModal")
        ).hide();

        resetStudentForm();
        fetchStudents();
    });
}

function editStudent(id) {
    fetch(`${STUDENT_API}/${id}`)
        .then(res => res.json())
        .then(s => {
            document.getElementById("studentId").value = s.id;
            document.getElementById("name").value = s.name;
            document.getElementById("department").value = s.department;
            document.getElementById("email").value = s.email;

            document.querySelectorAll("#courseCheckboxes input")
                .forEach(cb => cb.checked = s.courseIds?.includes(Number(cb.value)));

            new bootstrap.Modal(document.getElementById("studentModal")).show();
        });
}

function deleteStudent(id) {
    if (!confirm("Delete student?")) return;

    fetch(`${STUDENT_API}/${id}`, { method: "DELETE" })
        .then(fetchStudents);
}

function resetStudentForm() {
    document.getElementById("studentId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("department").value = "";
    document.getElementById("email").value = "";
    document.querySelectorAll("#courseCheckboxes input").forEach(cb => cb.checked = false);
}

/* -------- COURSES FOR ENROLLMENT -------- */

function loadCourseCheckboxes() {
    fetch(COURSE_API)
        .then(res => res.json())
        .then(data => {
            const div = document.getElementById("courseCheckboxes");
            div.innerHTML = "";

            data.forEach(c => {
                div.innerHTML += `
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="${c.id}">
                        <label class="form-check-label">
                            ${c.title} (${c.credit} credits)
                        </label>
                    </div>
                `;
            });
        });
}
