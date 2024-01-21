package com.oasis.course.controllers;


import com.oasis.course.domain.Course;
import com.oasis.course.domain.DTO.CourseDTO;
import com.oasis.course.exception.GenericException;
import com.oasis.course.services.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping(value = "/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService, CourseService courseService1) {
        this.courseService = courseService1;
    }

    @PostMapping()
    @ResponseBody()
    public ResponseEntity<Course> saveCourse(@RequestBody CourseDTO courseDTO) {
        try {
            Course savedCourse = this.courseService.saveCourse(courseDTO);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        try {
            Course updatedCourse = this.courseService.atualizarPorId(id, courseDTO);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id) {
        try {
            Course deletedCourse = this.courseService.deleteFisicamente(id);
            return new ResponseEntity<>(deletedCourse, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Course>> searchCourses(@RequestParam(required = false, defaultValue = "true") Boolean isPageable, Pageable pageable) {
        try {
            Page<Course> courses = this.courseService.pesquisar(pageable, isPageable);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        try {
            Course course = this.courseService.findbyId(id);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byCompany/{companyId}")
    public List<Course> getCourseByCompanyId(@PathVariable Long companyId) {
        return courseService.findAllByCompanyId(companyId);
    }
}
