package com.oasis.course.services;

import com.oasis.course.domain.DTO.VideoCommentDTO;
import com.oasis.course.domain.Video;
import com.oasis.course.domain.VideoComment;
import com.oasis.course.exception.GenericException;
import com.oasis.course.repositories.VideoCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class VideoCommentService {

    @Autowired
    VideoCommentRepository videoCommentRepository;

    @Autowired
    VideoService videoService;

    public VideoComment saveVideoComment(@RequestBody VideoCommentDTO videoCommentDTO) {
        VideoComment videoComment = new VideoComment();
        videoComment.setText(videoCommentDTO.getText());
        Video video = this.videoService.findById(videoCommentDTO.getVideoId());
        videoComment.setVideo(video);
        try {
            return this.videoCommentRepository.save(videoComment);
        } catch (NullPointerException n) {
            throw new GenericException("Save Error");
        }
    }

    public VideoComment updateById(@PathVariable Long videoCommentId, @RequestBody final VideoCommentDTO videoCommentDTO) throws Exception {

        VideoComment existingVideoComment = videoCommentRepository.findById(videoCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + videoCommentId));

        existingVideoComment.setText(videoCommentDTO.getText());


        return videoCommentRepository.save(existingVideoComment);
    }

    public VideoComment deleteFisicamente(@PathVariable Long videoCommentId) throws Exception {
        VideoComment existingVideoComment = videoCommentRepository.findById(videoCommentId)
                .orElseThrow(() -> new EntityNotFoundException("VideoComment não encontrado com o ID: " + videoCommentId));
        existingVideoComment.setDeleted(true);
        return videoCommentRepository.save(existingVideoComment);
    }

    public Page<VideoComment> findAll(Pageable pageable, Boolean isPageable) throws Exception {
        if (isPageable) {
            return this.videoCommentRepository.findAll(pageable);
        } else {
            return new PageImpl<>(this.videoCommentRepository.findAll());
        }
    }

    public VideoComment findById(Long id) {
        return this.videoCommentRepository.findById(id).orElseThrow(() -> new GenericException("BC-0003"));
    }

}
