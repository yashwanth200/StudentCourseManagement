package com.example.StudentCourseManagement.repository;

import com.example.StudentCourseManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
