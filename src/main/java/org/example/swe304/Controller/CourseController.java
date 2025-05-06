package org.example.swe304.Controller;

import org.example.swe304.Dtos.CourseDTO;
import org.example.swe304.Model.Course;
import org.example.swe304.Repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }



    @GetMapping
    public String listCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Course> coursePage = courseRepository.findAll(pageable);

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "Course/Course"; // Güncellenmiş Thymeleaf sayfasına yönlendirme
    }



    @GetMapping("/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "Course/CourseAdd";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute Course course) {
        courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/update/{id}")
    public String updateCourseForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ders bulunamadı! ID: " + id));
        model.addAttribute("course", course);
        return "Course/CourseUpdate";
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course course) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek ders bulunamadı! ID: " + id));

        existingCourse.setName(course.getName());
        existingCourse.setCode(course.getCode());
        existingCourse.setCredit(course.getCredit());

        courseRepository.save(existingCourse);
        return "redirect:/courses";
    }

    // delete metodunu oluştur
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }

}


