package com.springbootschoolmanager.service;

import com.springbootschoolmanager.repository.ClassRepository;
import com.springbootschoolmanager.exception._class.ClassNotFoundException;
import com.springbootschoolmanager.exception.student.StudentAlreadyExistsException;
import com.springbootschoolmanager.exception.student.StudentNotFoundException;
import com.springbootschoolmanager.entity.Student;
import com.springbootschoolmanager.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentService(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    public List<Student> getAllStudents() {
        return this.studentRepository.getAllStudents();
    }

    public void createStudent(final Student student) {
        LOGGER.debug("createStudent() looking for a student with a pesel {}", student.getPesel());
        if (studentRepository.getStudentByPesel(student.getPesel()) != null) {
            LOGGER.warn("createStudent() Student a with a pesel {} found, throwing StudentAlreadyExistsException",
                    student.getPesel());

            throw new StudentAlreadyExistsException(
                    String.format("Student with a pesel %s already exists", student.getPesel()));
        }

        LOGGER.info("createStudent() Student saved successfully");
        studentRepository.save(student);
    }

    public String deleteStudent(final String pesel) {
        checkIfStudentExists(pesel);
        studentRepository.deleteById(pesel);

        return String.format("Student with a pesel %s deleted successfully", pesel);
    }

    public List<Student> getAllStudentsByClassId(final String class_id) {
        return this.studentRepository.getAllStudentsByClassId(class_id);
    }

    public String assignStudentToClass(final String class_id, final String pesel) {
        checkIfClassExists(class_id);
        checkIfStudentExists(pesel);
        studentRepository.assignStudentToClass(class_id, pesel);

        return String.format("Student with a pesel %s assigned to a class %s successfully", pesel, class_id);
    }

    private boolean checkIfStudentExists(final String pesel) {
        studentRepository.findById(pesel)
                .orElseThrow(() -> new StudentNotFoundException(
                        String.format("Student with a pesel %s doesn't exist", pesel)));
        return true;
    }

    private boolean checkIfClassExists(String class_id) {
        Optional.ofNullable(classRepository.getClassByClassId(class_id))
                .orElseThrow(() -> new ClassNotFoundException(
                        String.format("Class with an id %s doesn't exist", class_id)));
        return true;
    }
}
