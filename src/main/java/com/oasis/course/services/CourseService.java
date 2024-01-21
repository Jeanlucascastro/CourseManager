package com.oasis.course.services;

import com.oasis.course.domain.Company;
import com.oasis.course.domain.Course;
import com.oasis.course.domain.DTO.CourseDTO;
import com.oasis.course.exception.GenericException;
import com.oasis.course.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CompanyService companyService;

    public Course saveCourse(@RequestBody CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        Company company = this.companyService.findbyId(courseDTO.getCompanyId());
        course.setCompany(company);
        course.setDescription(courseDTO.getDescription());
        return this.courseRepository.save(course);
    }

    public Course atualizarPorId(@PathVariable Long courseId, @RequestBody final CourseDTO courseDTO) throws Exception {

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + courseId));

        existingCourse.setName(courseDTO.getName());
        existingCourse.setDescription(courseDTO.getDescription());

        return courseRepository.save(existingCourse);
    }

    public Course deleteFisicamente(@PathVariable Long courseId) throws Exception {
            Course existingCourse = courseRepository.findById(courseId)
                    .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado com o ID: " + courseId));
            existingCourse.setDeleted(true);
            return courseRepository.save(existingCourse);
    }

    public Page<Course> pesquisar(Pageable pageable, Boolean isPageable) throws Exception {
        if (isPageable) {
            return this.courseRepository.findAll(pageable);
        } else {
            return new PageImpl<>(this.courseRepository.findAll());
        }
    }

    public Course findbyId(Long id) {
        return this.courseRepository.findById(id).orElseThrow(() -> new GenericException("BC-0003"));
    }

}
