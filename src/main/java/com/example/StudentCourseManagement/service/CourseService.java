package com.example.StudentCourseManagement.service;

import com.example.StudentCourseManagement.Mapper.MapperUtil;
import com.example.StudentCourseManagement.dto.CourseDTO;
import com.example.StudentCourseManagement.model.Course;
import com.example.StudentCourseManagement.model.Student;
import com.example.StudentCourseManagement.repository.CourseRepository;
import com.example.StudentCourseManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepo;

    public Course addCourse(Course course){
        return courseRepository.save(course);
    }

    public List<CourseDTO> getAllCourse(){
        List<Course> courses =  courseRepository.findAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();
        for(Course course: courses){
            courseDTOs.add(MapperUtil.toCourseDto(course));
        }
        return courseDTOs;
    }

    public Course getCourseById(Long courseId){
        Course courseById = courseRepository.findById(courseId)
                .orElseThrow(()->
                        new RuntimeException("Course with id "+ courseId + " not present"));

        return courseById;
    }

    public CourseDTO updateCourse(Long id,CourseDTO courseDTO){
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course with + "+id + "not present"));
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setCredit(courseDTO.getCredit());
        existingCourse.getStudents().
                forEach(student->student.getCourses().remove(existingCourse));
        existingCourse.getStudents().clear();
        Set<Student> newStudents= new HashSet<>();
        if(courseDTO.getStudentIds()!=null) {
            for (Long studentId : courseDTO.getStudentIds()) {
                newStudents.add(studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student with id " + studentId + "not present")));
            }
        }
        existingCourse.setStudents(newStudents);
        newStudents.
                forEach(student->student.getCourses().add(existingCourse));
        Course updatedCourse = courseRepository.save(existingCourse);
        return MapperUtil.toCourseDto(updatedCourse);
    }

    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course with + "+id + "not present"));
        course.getStudents().
                forEach(student -> student.getCourses().remove(course));
        course.getStudents().clear();
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
