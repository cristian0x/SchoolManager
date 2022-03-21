package com.springbootschoolmanager._class;

import com.springbootschoolmanager.entity._Class;
import com.springbootschoolmanager.exception._class.ClassAlreadyExistsException;
import com.springbootschoolmanager.repository.ClassRepository;
import com.springbootschoolmanager.service.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock
    private ClassRepository classRepository;

    private ClassService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClassService(classRepository);
    }

    @Test
    void getAllClasses() {
        // when
        underTest.getAllClasses();

        // then
        verify(classRepository).getAllClasses();
    }

    @Test
    void createClass() {
        // given
        _Class _class = new _Class("3a");

        // when
        underTest.createClass(_class);

        // then
        ArgumentCaptor<_Class> classArgumentCaptor = ArgumentCaptor.forClass(_Class.class);
        verify(classRepository).save(classArgumentCaptor.capture());

        _Class capturedClass = classArgumentCaptor.getValue();
        assertThat(capturedClass).isEqualTo(_class);
    }

    @Test
    void createClass_ShouldThrowClassAlreadyExistsException() {
        // given
        _Class _class = new _Class("3a");

        // when
        given(classRepository.getClassByClassId(_class.getId())).willReturn(_class);

        // then
        assertThatThrownBy(() -> underTest.createClass(_class))
                .isInstanceOf(ClassAlreadyExistsException.class)
                .hasMessageContaining(String.format("Class with an id: %s already exists", _class.getId()));
        verify(classRepository, never()).save(any());
    }
}