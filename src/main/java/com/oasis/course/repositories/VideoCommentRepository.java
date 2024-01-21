package com.oasis.course.repositories;

import com.oasis.course.domain.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {

}
