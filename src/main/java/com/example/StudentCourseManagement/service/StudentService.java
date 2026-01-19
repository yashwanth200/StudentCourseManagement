package com.example.StudentCourseManagement.service;

import com.example.StudentCourseManagement.model.Student;
import com.example.StudentCourseManagement.repository.CourseRepository;
import com.example.StudentCourseManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student){

        return studentRepository.save(student);
    }

}
