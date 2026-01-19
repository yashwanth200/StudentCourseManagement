package com.example.StudentCourseManagement.repository;

import com.example.StudentCourseManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
