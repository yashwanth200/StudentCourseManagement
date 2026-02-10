package com.example.StudentCourseManagement.Mapper;

import com.example.StudentCourseManagement.dto.StudentDTO;
import com.example.StudentCourseManagement.model.Course;
import com.example.StudentCourseManagement.model.Student;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MapperUtil {

    public static StudentDTO toStudentDto(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setDepartment(student.getDepartment());

        Set<Long> courseIds = new HashSet<>();
        for(Course course: student.getCourses()){
            courseIds.add(course.getId());
        }
        studentDTO.setCourseIds(courseIds);
        return studentDTO;
    }
}
