package org.example.swe304.Services;

import org.example.swe304.Dtos.StudentDTO;
import org.example.swe304.Dtos.StudentDTO_UI;
import org.example.swe304.Model.Student;

import java.util.List;

public interface StudentService {

    StudentDTO_UI save(StudentDTO_UI student);

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(Long id);

    void deleteStudent(Long id);

    StudentDTO_UI updateStudent(Long id, StudentDTO_UI studentDTOui);

    void validateBirthDate(StudentDTO_UI studentDTOui);
}