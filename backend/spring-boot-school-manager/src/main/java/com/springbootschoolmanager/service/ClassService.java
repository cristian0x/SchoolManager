package com.springbootschoolmanager.service;

import com.springbootschoolmanager.repository.ClassRepository;
import com.springbootschoolmanager.entity._Class;
import com.springbootschoolmanager.exception._class.ClassAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassService.class);

    private final ClassRepository classRepository;

    public List<_Class> getAllClasses() {
        return classRepository.getAllClasses();
    }

    public void createClass(final _Class _class) {
        checkIfClassExists(_class.getId());
        classRepository.save(_class);
        LOGGER.info("createClass() Class saved successfully");
    }

    private boolean checkIfClassExists(final String class_id) {
        LOGGER.debug("checkIfClassExists() looking for a class with a class_id {}", class_id);
        if (classRepository.getClassByClassId(class_id) != null) {
            LOGGER.warn("checkIfClassExists() Class with a class_id {} found, throwing ClassAlreadyExistsException",
                    class_id);

            throw new ClassAlreadyExistsException(
                    String.format("Class with an id: %s already exists", class_id));
        }
        return true;
    }
}
