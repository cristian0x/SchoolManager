package com.springbootschoolmanager.student;

import com.springbootschoolmanager.entity.Student;
import com.springbootschoolmanager.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @BeforeEach
    public void initEach(){
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );
        Student student1 = new Student(
                "00000000000",
                "Frank",
                "Test",
                "3c"
        );
        Student student2 = new Student(
                "55555555555",
                "Hank",
                "Test",
                "3d"
        );
        underTest.save(student);
        underTest.save(student1);
        underTest.save(student2);
    }

    @Test
    void getAllStudents() {
        // when
        List<Student> studentList = underTest.getAllStudents();

        // then
        assertThat(studentList.size()).isNotEqualTo(1);
        assertThat(studentList.size()).isNotEqualTo(2);
        assertThat(studentList.size()).isEqualTo(3);
    }

    @Test
    void getAllStudentsByClassId() {
        // when
        List<Student> studentListFor3bClass = underTest.getAllStudentsByClassId("3b");
        List<Student> studentListFor3aClass = underTest.getAllStudentsByClassId("3a");
        List<Student> studentListFor3cClass = underTest.getAllStudentsByClassId("3c");

        // then
        assertThat(studentListFor3bClass.size()).isEqualTo(1);
        assertThat(studentListFor3aClass.size()).isEqualTo(0);
        assertThat(studentListFor3cClass.size()).isEqualTo(1);
    }

    @Test
    void getStudentByPesel() {
        // when
        Student student = underTest.getStudentByPesel("99999999999");
        Student student1 = underTest.getStudentByPesel("00000000000");
        Student student2 = underTest.getStudentByPesel("55555555555");
        Student random = underTest.getStudentByPesel("123456789000");

        // then
        assertThat(student).isNotNull();
        assertThat(student1).isNotNull();
        assertThat(student2).isNotNull();
        assertThat(random).isNull();
    }

    @Test
    @Disabled
    void assignStudentToClass() {
        // when
        Student student = underTest.getStudentByPesel("99999999999");
        assertThat(student.getClass_id()).isEqualTo("3b");

        underTest.assignStudentToClass("3f", "99999999999");
        assertThat(student.getClass_id()).isEqualTo("3f");
    }
}