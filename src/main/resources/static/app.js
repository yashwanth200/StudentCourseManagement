const API_URL = "http://localhost:8080/api/courses";

document.addEventListener("DOMContentLoaded", loadCourses);

const form = document.getElementById("courseForm");

form.addEventListener("submit", function (e) {
    e.preventDefault();

    const id = document.getElementById("courseId").value;
    const title = document.getElementById("title").value;
    const credit = document.getElementById("credit").value;

    const course = { title, credit };

    if (id) {
        updateCourse(id, course);
    } else {
        createCourse(course);
    }
});

function loadCourses() {
    fetch(API_URL)
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("courseTable");
            table.innerHTML = "";

            data.forEach(course => {
                table.innerHTML += `
                    <tr>
                        <td>${course.id}</td>
                        <td>${course.title}</td>
                        <td>${course.credit}</td>
                        <td class="actions">
                            <button onclick="editCourse(${course.id}, '${course.title}', ${course.credit})">Edit</button>
                            <button onclick="deleteCourse(${course.id})">Delete</button>
                        </td>
                    </tr>
                `;
            });
        });
}

function createCourse(course) {
    fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(course)
    }).then(() => {
        resetForm();
        loadCourses();
    });
}

function updateCourse(id, course) {
    fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(course)
    }).then(() => {
        resetForm();
        loadCourses();
    });
}

function deleteCourse(id) {
    if (!confirm("Are you sure?")) return;

    fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    }).then(loadCourses);
}

function editCourse(id, title, credit) {
    document.getElementById("courseId").value = id;
    document.getElementById("title").value = title;
    document.getElementById("credit").value = credit;
}

function resetForm() {
    document.getElementById("courseId").value = "";
    form.reset();
}

function searchCourses() {
    const title = document.getElementById("searchTitle").value;
    const credit = document.getElementById("searchCredit").value;
    
    let url = API_URL;
    
    if (title && credit) {
        url = `${API_URL}/search?title=${encodeURIComponent(title)}&credit=${credit}`;
    } else if (title) {
        url = `${API_URL}/courses?title=${encodeURIComponent(title)}`;
    } else if (credit) {
        url = `${API_URL}/filter?credit=${credit}`;
    }
    
    fetch(url)
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("courseTable");
            table.innerHTML = "";

            data.forEach(course => {
                table.innerHTML += `
                    <tr>
                        <td>${course.id}</td>
                        <td>${course.title}</td>
                        <td>${course.credit}</td>
                        <td class="actions">
                            <button onclick="editCourse(${course.id}, '${course.title}', ${course.credit})">Edit</button>
                            <button onclick="deleteCourse(${course.id})">Delete</button>
                        </td>
                    </tr>
                `;
            });
        });
}

function clearSearch() {
    document.getElementById("searchTitle").value = "";
    document.getElementById("searchCredit").value = "";
    loadCourses();
}
