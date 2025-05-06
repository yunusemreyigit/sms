package org.example.swe304.Repository;

import org.example.swe304.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>  , PagingAndSortingRepository<Course, Long> {
}
