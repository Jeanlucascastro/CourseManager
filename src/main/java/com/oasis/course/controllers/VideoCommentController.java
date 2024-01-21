package com.oasis.course.controllers;

import com.oasis.course.domain.DTO.VideoCommentDTO;
import com.oasis.course.domain.VideoComment;
import com.oasis.course.exception.GenericException;
import com.oasis.course.services.VideoCommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping(value = "/video-comment")
public class VideoCommentController {

    @Autowired
    private VideoCommentService videoCommentService;

    @Autowired
    public VideoCommentController(VideoCommentService videoCommentService) {
    }

    @PostMapping()
    @ResponseBody()
    public ResponseEntity<VideoComment> saveVideoComment(@RequestBody VideoCommentDTO videoCommentDTO) {
        try {
            VideoComment savedVideoComment = this.videoCommentService.saveVideoComment(videoCommentDTO);
            return new ResponseEntity<>(savedVideoComment, HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public ResponseEntity<VideoComment> updateVideoComment(@PathVariable Long id, @RequestBody VideoCommentDTO videoCommentDTO) {
        try {
            VideoComment updatedVideoComment = this.videoCommentService.updateById(id, videoCommentDTO);
            return new ResponseEntity<>(updatedVideoComment, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<VideoComment> deleteVideoComment(@PathVariable Long id) {
        try {
            VideoComment deletedVideoComment = this.videoCommentService.deleteFisicamente(id);
            return new ResponseEntity<>(deletedVideoComment, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<Page<VideoComment>> searchVideoComments(@RequestParam(required = false, defaultValue = "true") Boolean isPageable, Pageable pageable) {
        try {
            Page<VideoComment> videoComments = this.videoCommentService.findAll(pageable, isPageable);
            return new ResponseEntity<>(videoComments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VideoComment> getVideoCommentById(@PathVariable Long id) {
        try {
            VideoComment videoComment = this.videoCommentService.findById(id);
            return new ResponseEntity<>(videoComment, HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
