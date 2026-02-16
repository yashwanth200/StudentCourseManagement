package com.example.StudentCourseManagement.controller;

import com.example.StudentCourseManagement.dto.CourseDTO;
import com.example.StudentCourseManagement.model.Course;
import com.example.StudentCourseManagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<CourseDTO> getAllCourse(){
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

    @GetMapping("/count")
    public Long getCourseCount(){
        return courseService.getCourseCount();
    }

    /*
    @GetMapping("/courses")
    public List<Course> getCourseByTitle(@RequestParam(required = false) String title){
        if (title != null && !title.trim().isEmpty()) {
            return courseService.getCourseByTitle(title);
        }
        return courseService.getAllCourse();
    }
     */

    @GetMapping("/filter")
    public List<Course> findByCredit(@RequestParam Integer credit){
        return courseService.getCourseByCredit(credit);

    }

    @GetMapping("/search")
    public List<Course> getCourseByTitleAndCredit(
            @RequestParam String title,
            @RequestParam Integer credit){
        return courseService.getCourseByTitleAndCredit(title,credit);
    }




}
