package com.example.StudentCourseManagement.controller;

import com.example.StudentCourseManagement.dto.StudentDTO;
import com.example.StudentCourseManagement.model.Student;
import com.example.StudentCourseManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("/{id}")
    public StudentDTO updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO studentDTO){
        return studentService.updateStudent(id,studentDTO);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }




}
