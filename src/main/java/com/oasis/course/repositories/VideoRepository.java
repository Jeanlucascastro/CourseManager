package com.oasis.course.repositories;


import com.oasis.course.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {


    List<Video> findAllByCourseId(Long courseId);

}
