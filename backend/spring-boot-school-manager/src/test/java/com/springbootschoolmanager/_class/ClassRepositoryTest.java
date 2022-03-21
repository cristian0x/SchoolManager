package com.springbootschoolmanager._class;

import com.springbootschoolmanager.entity._Class;
import com.springbootschoolmanager.repository.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ClassRepositoryTest {

    @Autowired
    private ClassRepository underTest;

    @BeforeEach
    public void initEach() {
        // given
        _Class _class = new _Class("3a");
        _Class _class1 = new _Class("3b");
        _Class _class2 = new _Class("3c");
        _Class _class3 = new _Class("3d");
        _Class _class4 = new _Class("3e");
        _Class _class5 = new _Class("3f");
        _Class _class6 = new _Class("3g");
        _Class _class7 = new _Class("3h");
        _Class _class8 = new _Class("3i");

        underTest.save(_class);
        underTest.save(_class1);
        underTest.save(_class2);
        underTest.save(_class3);
        underTest.save(_class4);
        underTest.save(_class5);
        underTest.save(_class6);
        underTest.save(_class7);
        underTest.save(_class8);
    }

    @Test
    void getAllClasses() {
        // when
        List<_Class> classList = underTest.getAllClasses();

        // then
        assertThat(classList.size()).isEqualTo(9);
    }

    @Test
    void getClassByClassId() {
        // when
        _Class _class = underTest.getClassByClassId("3a");
        _Class _class1 = underTest.getClassByClassId("3b");
        _Class _class2 = underTest.getClassByClassId("3c");
        _Class _class3 = underTest.getClassByClassId("3d");
        _Class _class4 = underTest.getClassByClassId("3e");
        _Class _class5 = underTest.getClassByClassId("3f");
        _Class _class6 = underTest.getClassByClassId("3g");
        _Class _class7 = underTest.getClassByClassId("3h");
        _Class _class8 = underTest.getClassByClassId("3i");

        // then
        assertThat(_class.getId()).isEqualTo("3a");
        assertThat(_class1.getId()).isEqualTo("3b");
        assertThat(_class2.getId()).isEqualTo("3c");
        assertThat(_class3.getId()).isEqualTo("3d");
        assertThat(_class4.getId()).isEqualTo("3e");
        assertThat(_class5.getId()).isEqualTo("3f");
        assertThat(_class6.getId()).isEqualTo("3g");
        assertThat(_class7.getId()).isEqualTo("3h");
        assertThat(_class8.getId()).isEqualTo("3i");
    }
}