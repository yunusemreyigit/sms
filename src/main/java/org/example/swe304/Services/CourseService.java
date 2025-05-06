package org.example.swe304.Services;

import org.example.swe304.Dtos.CourseAddDTO;
import org.example.swe304.Dtos.CourseDTO;
import org.example.swe304.Model.Course;
import org.example.swe304.Repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void addCourse(CourseAddDTO course) {
        Course newCourse = new Course();
        newCourse.setName(course.getName());
        newCourse.setCode(course.getCode());
        newCourse.setCredit(course.getCredit());

        courseRepository.save(newCourse);
    }
}
