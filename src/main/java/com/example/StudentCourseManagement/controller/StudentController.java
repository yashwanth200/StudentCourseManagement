package com.example.StudentCourseManagement.controller;

import com.example.StudentCourseManagement.dto.StudentDTO;
import com.example.StudentCourseManagement.model.Student;
import com.example.StudentCourseManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody StudentDTO studentDTO){
        return studentService.createStudent(studentDTO);
    }
}
