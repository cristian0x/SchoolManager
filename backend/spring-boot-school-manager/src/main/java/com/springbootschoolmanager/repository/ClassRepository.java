package com.springbootschoolmanager.repository;

import com.springbootschoolmanager.entity._Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<_Class, String> {

    @Query(value = "SELECT * FROM class", nativeQuery = true)
    List<_Class> getAllClasses();

    @Query(value = "SELECT * FROM class WHERE id = :class_id", nativeQuery = true)
    _Class getClassByClassId(@Param("class_id") String class_id);
}
