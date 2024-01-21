package com.oasis.course.repositories;

import com.oasis.course.domain.Course;
import com.oasis.course.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByCompanyId(Long companyId);

}
