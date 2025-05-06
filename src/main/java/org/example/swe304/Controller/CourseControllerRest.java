package org.example.swe304.Controller;

import org.example.swe304.Dtos.CourseAddDTO;
import org.example.swe304.Dtos.CourseDTO;
import org.example.swe304.Model.Course;
import org.example.swe304.Services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseControllerRest {
    private final CourseService courseService;

    public CourseControllerRest(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ResponseEntity<CourseAddDTO> addCourse(@RequestBody CourseAddDTO course) {
        courseService.addCourse(course);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }
}
