package com.example.StudentCourseManagement.service;

import com.example.StudentCourseManagement.Mapper.MapperUtil;
import com.example.StudentCourseManagement.dto.StudentDTO;
import com.example.StudentCourseManagement.model.Course;
import com.example.StudentCourseManagement.model.Student;
import com.example.StudentCourseManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseService courseService;


    @Autowired
    private MapperUtil mapperUtil;

    public StudentDTO createStudent(StudentDTO studentDTO){
        Student  student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setDepartment(studentDTO.getDepartment());
        //courseIds = [1,2,3]
        //for loop courseIds
        // 1-> courseFindbyId(1) -->
        Set<Course> courses = new HashSet<>();

        for(Long id : studentDTO.getCourseIds()){
            courses.add(courseService.getCourseById(id));// 1 -> course with id =1
        }

        student.setCourses(courses);
        courses.forEach(course -> course.getStudents().add(student));
        Student savedStudent = studentRepository.save(student);
        return MapperUtil.toStudentDto(savedStudent);
    }

    public List<StudentDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student : students){
            studentDTOS.add(MapperUtil.toStudentDto(student));
        }

        return studentDTOS;

    }


}
