package com.oasis.course.services;

import com.oasis.course.domain.Course;
import com.oasis.course.domain.DTO.VideoDTO;
import com.oasis.course.domain.Video;
import com.oasis.course.exception.GenericException;
import com.oasis.course.repositories.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class VideoService {

    VideoRepository videoRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    CompanyService companyService;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Video saveVideo(@RequestBody VideoDTO videoDTO) {
        Video video = new Video();
        video.setName(videoDTO.getName());
        video.setUrl(videoDTO.getUrl());
        video.setOrdering(videoDTO.getOrdering());
        Optional<Course> course = Optional.ofNullable(this.courseService.findbyId(videoDTO.getCourseId()));
        course.ifPresent(video::setCourse);
        return this.videoRepository.save(video);
    }

    public Video atualizarPorId(@PathVariable Long videoId, @RequestBody final VideoDTO videoDTO) throws Exception {

        Video existingVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + videoId));

        existingVideo.setName(videoDTO.getName());
        existingVideo.setUrl(videoDTO.getUrl());
        existingVideo.setOrdering(videoDTO.getOrdering());

        return videoRepository.save(existingVideo);
    }

    public Video deleteFisicamente(@PathVariable Long videoId) throws Exception {
        Video existingVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video não encontrado com o ID: " + videoId));
        existingVideo.setDeleted(true);
        return videoRepository.save(existingVideo);
    }

    public Page<Video> pesquisar(Pageable pageable, Boolean isPageable) throws Exception {
        if (isPageable) {
            return this.videoRepository.findAll(pageable);
        } else {
            return new PageImpl<>(this.videoRepository.findAll());
        }
    }

    public Video findById(Long id) {
        return this.videoRepository.findById(id).orElseThrow(() -> new GenericException("BC-0003"));
    }

    public List<Video> findVideosByCourseId(Long courseId) {
        return videoRepository.findAllByCourseId(courseId);
    }

}
