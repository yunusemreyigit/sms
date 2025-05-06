package org.example.swe304.Controller;

import jakarta.validation.Valid;
import org.example.swe304.Dtos.StudentDTO;
import org.example.swe304.Dtos.StudentDTO_UI;
import org.example.swe304.Model.Student;
import org.example.swe304.Repository.StudentRepository;
import org.example.swe304.Services.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    // 📌 Tüm öğrencileri listeleme (HTML)
    @GetMapping
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);

        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "Student/Student"; // Güncellenmiş Thymeleaf sayfasına yönlendirme
    }

    // 📌 Yeni öğrenci ekleme formu (HTML)
    @GetMapping("/add")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "Student/StudentAdd";
    }

    // 📌 Yeni öğrenci kaydetme işlemi (HTML)
    @PostMapping("/add")
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "Student/StudentAdd";
        }
        studentRepository.save(student);
        return "redirect:/students";
    }

    // 📌 Öğrenci güncelleme formunu açma (HTML)
    @GetMapping("/update/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı! ID: " + id));

        model.addAttribute("student", student);
        return "Student/StudentUpdate";
    }

    // 📌 Öğrenci güncelleme (HTML)
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "Student/StudentUpdate";
        }
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek öğrenci bulunamadı! ID: " + id));

        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setStudentNumber(student.getStudentNumber());
        existingStudent.setGrade(student.getGrade());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setBirthDate(student.getBirthDate());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setIdentityNumber(student.getIdentityNumber());
        existingStudent.setGender(student.getGender());

        studentRepository.save(existingStudent);
        return "redirect:/students";
    }

    // 📌 Öğrenci silme işlemi (HTML)
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Silinecek öğrenci bulunamadı! ID: " + id);
        }
        studentRepository.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/json")
    @ResponseBody
    public ResponseEntity<List<StudentDTO>> getAllStudentsJson() {
        // 📌 Tüm öğrencileri listeleme (JSON)
        List<StudentDTO> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    // 📌 Yeni öğrenci ekleme (JSON)
    @PostMapping("/json/add")
    @ResponseBody
    public ResponseEntity<StudentDTO_UI> addStudentJson(@Valid @RequestBody StudentDTO_UI studentDTOui, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        StudentDTO_UI savedStudent = studentService.save(studentDTOui);
        return ResponseEntity.status(201).body(savedStudent);
    }

    // 📌 Öğrenci güncelleme (JSON)
    @PutMapping("/json/update/{id}")
    @ResponseBody
    public ResponseEntity<StudentDTO_UI> updateStudentJson(@PathVariable Long id, @Valid @RequestBody StudentDTO_UI studentDTOui, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        StudentDTO existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        StudentDTO_UI updatedStudent = new StudentDTO_UI();
        BeanUtils.copyProperties(studentDTOui, updatedStudent);
        StudentDTO_UI updatedStudentDTO = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(updatedStudentDTO);
    }

    // 📌 Öğrenci silme (JSON)
    @DeleteMapping("/json/delete/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteStudentJson(@PathVariable Long id) {
        if (studentService.getStudentById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    // 📌 Öğrenci ID ile getirme (JSON)
    @GetMapping("/json/{id}")
    @ResponseBody
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        // DTO'ya tüm alanları eklediğinden emin ol
        StudentDTO dto = new StudentDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getStudentNumber(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getBirthDate(),
                student.getGender()
        );

        return ResponseEntity.ok(dto);
    }
}