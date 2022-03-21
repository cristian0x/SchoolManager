package com.springbootschoolmanager.repository;

import com.springbootschoolmanager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Query(value = "SELECT * FROM student", nativeQuery = true)
    List<Student> getAllStudents();

    @Query(value = "SELECT * FROM student WHERE class_id = :class_id", nativeQuery = true)
    List<Student> getAllStudentsByClassId(@Param("class_id") String class_id);

    @Query(value = "SELECT * FROM student WHERE pesel = :pesel", nativeQuery = true)
    Student getStudentByPesel(@Param("pesel") String pesel);

    @Transactional
    @Modifying
    @Query(value = "UPDATE student SET class_id = :class_id WHERE pesel = :pesel", nativeQuery = true)
    void assignStudentToClass(@Param("class_id") String class_id, @Param("pesel") String pesel);
}
