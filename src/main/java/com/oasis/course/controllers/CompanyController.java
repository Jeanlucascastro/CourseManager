package com.oasis.course.controllers;


import com.oasis.course.domain.Company;
import com.oasis.course.domain.DTO.CompanyDTO;
import com.oasis.course.exception.GenericException;
import com.oasis.course.services.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping(value = "/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping()
    @ResponseBody()
    public ResponseEntity<Company> saveCourse(@RequestBody CompanyDTO companyDTO) {
        try {
            Company savedCompany = this.companyService.saveCompany(companyDTO);
            return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public ResponseEntity<Company> updateCourse(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        try {
            Company updatedCompany = this.companyService.atualizarPorId(id, companyDTO);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable Long id) {
        try {
            Company deletedCompany = this.companyService.deleteFisicamente(id);
            return new ResponseEntity<>(deletedCompany, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Company>> searchCourses(@RequestParam(required = false, defaultValue = "true") Boolean isPageable, Pageable pageable) {
        try {
            Page<Company> companies = this.companyService.pesquisar(pageable, isPageable);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Company> getCourseById(@PathVariable Long id) {
        try {
            Company company = this.companyService.findbyId(id);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
