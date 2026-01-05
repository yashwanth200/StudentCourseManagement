package com.example.StudentCourseManagement.repository;

import com.example.StudentCourseManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTitleContainingIgnoringCase(String title);
    List<Course> findByCredit(Integer credit);

    List<Course> findByTitleContainingIgnoringCaseAndCredit(String title, Integer credit);
}
