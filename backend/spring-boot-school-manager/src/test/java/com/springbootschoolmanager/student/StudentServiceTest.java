package com.springbootschoolmanager.student;

import com.springbootschoolmanager.entity.Student;
import com.springbootschoolmanager.repository.ClassRepository;
import com.springbootschoolmanager.entity._Class;
import com.springbootschoolmanager.exception._class.ClassNotFoundException;
import com.springbootschoolmanager.exception.student.StudentAlreadyExistsException;
import com.springbootschoolmanager.exception.student.StudentNotFoundException;
import com.springbootschoolmanager.repository.StudentRepository;
import com.springbootschoolmanager.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassRepository classRepository;

    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository, classRepository);
    }

    @Test
    void getAllStudents() {
        // when
        underTest.getAllStudents();

        // then
        // the studentRepository was invoked using the method getAllStudents()
        verify(studentRepository).getAllStudents();
    }

    @Test
    void createStudent() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );

        // when
        underTest.createStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void createStudent_ShouldThrowStudentAlreadyExistsException() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );

        given(studentRepository.getStudentByPesel(student.getPesel())).willReturn(student);

        // when
        // then
        assertThatThrownBy(() -> underTest.createStudent(student))
                .isInstanceOf(StudentAlreadyExistsException.class)
                .hasMessageContaining(String.format("Student with pesel %s already exists", student.getPesel()));

        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudent() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );

        given(studentRepository.findById(student.getPesel())).willReturn(Optional.of(student));

        // when
        underTest.deleteStudent(student.getPesel());

        // then
        ArgumentCaptor<String> studentArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(studentRepository).deleteById(studentArgumentCaptor.capture());

        String capturedPesel = studentArgumentCaptor.getValue();
        assertThat(capturedPesel).isEqualTo(student.getPesel());
    }

    @Test
    void deleteStudentWillThrowStudentNotFoundException() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );

        given(studentRepository.findById(student.getPesel())).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(student.getPesel()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining(String.format("Student with pesel %s doesn't exist", student.getPesel()));

        verify(studentRepository, never()).deleteById(any());
    }

    @Test
    void getAllStudentsByClassId() {
        // when
        underTest.getAllStudentsByClassId("3f");

        // then
        verify(studentRepository).getAllStudentsByClassId("3f");
    }

    @Test
    void assignStudentToClass() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );
        _Class _class = new _Class("3a");

        given(classRepository.getClassByClassId("3a")).willReturn(_class);
        given(studentRepository.findById(student.getPesel())).willReturn(Optional.of(student));

        // when
        // then
        String response = underTest.assignStudentToClass("3a", student.getPesel());
        assertThat(response).isEqualTo(
                String.format("Student with pesel %s assigned to class %s successfully", student.getPesel(), "3a"));

    }

    @Test
    void assignStudentToClass_ShouldThrowClassNotFoundException() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );
        _Class _class = new _Class("3a");

        given(classRepository.getClassByClassId("3a")).willReturn(null);

        assertThatThrownBy(() -> underTest.assignStudentToClass(_class.getId(), student.getPesel()))
                .isInstanceOf(ClassNotFoundException.class)
                .hasMessageContaining(String.format("Class with id %s doesn't exist", _class.getId()));

        verify(studentRepository, never()).assignStudentToClass(any(), any());
    }

    @Test
    void assignStudentToClass_ShouldThrowStudentNotFoundException() {
        // given
        Student student = new Student(
                "99999999999",
                "Johny",
                "Test",
                "3b"
        );
        _Class _class = new _Class("3a");

        given(classRepository.getClassByClassId("3a")).willReturn(_class);
        given(studentRepository.findById(student.getPesel())).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.assignStudentToClass(_class.getId(), student.getPesel()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining(String.format("Student with pesel %s doesn't exist", student.getPesel()));

        verify(studentRepository, never()).assignStudentToClass(any(), any());
    }
}