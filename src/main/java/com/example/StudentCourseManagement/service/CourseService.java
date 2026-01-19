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

    public Course getCourseById(Long courseId){
        Course courseById = courseRepository.findById(courseId)
                .orElseThrow(()->
                        new RuntimeException("Course with id "+ courseId + " not present"));
        return courseById;
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
    public Long getCourseCount(){
        List<Course> courses = courseRepository.findAll();
        return (long) courses.size();
    }

    public List<Course> getCourseByTitle(String title){
        return courseRepository.findByTitleContainingIgnoringCase(title);
    }

    public List<Course> getCourseByCredit(Integer credit){
        return courseRepository.findByCredit(credit);
    }

    public List<Course> getCourseByTitleAndCredit(String title,Integer credit){
        return courseRepository.findByTitleContainingIgnoringCaseAndCredit(title,credit);
    }
}
