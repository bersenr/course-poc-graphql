package com.federated.course.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.federated.course.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

}
