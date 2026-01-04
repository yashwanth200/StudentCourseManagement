package com.example.StudentCourseManagement.service;

import com.example.StudentCourseManagement.model.Course;
import com.example.StudentCourseManagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }
    public List<Course> getAllCourse(){
        return courseRepository.findAll();
    }

    public Course updateCourse(Long id,Course course){
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course with + "+id + "not present"));
        existingCourse.setTitle(course.getTitle());
        existingCourse.setCredit(course.getCredit());
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id){
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course with + "+id + "not present"));
        courseRepository.deleteById(id);
    }
}
