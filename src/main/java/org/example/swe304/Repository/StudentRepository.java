package org.example.swe304.Repository;

import org.example.swe304.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
    // 📌 Öğrenci adına göre arama
    Student findByFirstName(String firstName);

    //query kullanarak kendi sorgularımızı da yazabiliriz
    // hql : hibernate query language bunu kullanırken sınıf adı ve değişken isimleri ile kullanırız
    // native query : true olursa normal  sql sorgusu yazabiliriz
    // native query default olarak false gelir bunu ayarlayabiliriz
    // @Query("select s from Student s where s.firstName = :firstName") gibi
    // bi tane hql sorgusu örneği ver query anotasyonu için
    // @Query("select s from Student s where s.firstName = :firstName")
    // @Query(value = "select * from student where first_name = :firstName", nativeQuery = true) gibi
}
