const API_URL = "http://localhost:8080/api/courses";

document.addEventListener("DOMContentLoaded", loadCourses);
const form = document.getElementById("courseForm");
form.addEventListener("submit", function (e){
    e.preventDefault();
    const id = document.getElementById("courseId").value;
    const title = document.getElementById("courseTitle").value;
    const credit = document.getElementById("credit").value;
    const course = {title, credit};
    createCourse(course);
});
function createCourse(course){
    fetch(API_URL,{
    method: "POST",
    headers: { "Content-Type" : "application/json"},
    body: JSON.stringify(course)
    })
    .then(() =>{
    loadCourses();
    });
}
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
                        <td>
                        <button onClick="editCourse(${course.id}, '${course.title}', ${course.credit})">Edit</button>
                        <button onClick="deleteCourse(${course.id})">Delete</button>
                        </td>

                    </tr>
                `;
            });
        });
}
