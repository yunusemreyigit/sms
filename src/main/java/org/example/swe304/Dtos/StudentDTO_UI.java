package org.example.swe304.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.example.swe304.Enums.Gender;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public class StudentDTO_UI{

    @NotNull(message = "Ad boş olamaz")
    @NotBlank(message = "Ad boş olamaz")
    @Size(min = 2, max = 50, message = "Ad en az 2 en fazla 50 karakter olabilir")
    private String firstName;

    @NotNull(message = "Soyad boş olamaz")
    @NotBlank(message = "Soyad boş olamaz")
    @Size(min = 2, max = 50, message = "Soyad en az 2 en fazla 50 karakter olabilir")
    private String lastName;

    @NotNull(message = "Öğrenci numarası boş olamaz")
    @NotBlank(message = "Öğrenci numarası boş olamaz")
    @Size(min = 2, max = 50, message = "Öğrenci numarası en az 2 en fazla 50 karakter olabilir")
    private String studentNumber;

    @NotNull(message = "Öğrenci maili boş olamaz")
    @NotBlank(message = "Öğrenci maili boş olamaz")
    @Size(min = 2, max = 50, message = "Öğrenci maili en az 2 en fazla 50 karakter olabilir")
    @Email(message = "Geçerli bir mail adresi giriniz")
    private String email;

    @NotNull(message = "Telefon numarası boş olamaz")
    @NotBlank(message = "Telefon numarası boş olamaz")
    @Size(min = 10, max = 10, message = "Telefon numarası 10 karakterli olmalıdır")
    private String phoneNumber;

    @NotNull(message = "Doğum Tarihi boş olamaz")
    @PastOrPresent(message = "Doğum tarihi geçmiş bir tarih olmalıdır")
    @Column(name = "birthdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "Cinsiyet boş olamaz")
    @Column(name = "gender")
    private Gender gender;

    public StudentDTO_UI(String firstName, String lastName, String studentNumber, String email, String phoneNumber, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public StudentDTO_UI() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}