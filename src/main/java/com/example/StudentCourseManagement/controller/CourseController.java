package com.example.StudentCourseManagement.controller;

import com.example.StudentCourseManagement.model.Course;
import com.example.StudentCourseManagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    public CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }
    @GetMapping
    public List<Course> getAllCourse(){
        return courseService.getAllCourse();
    }

    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable Long id,
            @RequestBody Course course){
        return courseService.updateCourse(id, course);
    }
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }

}
