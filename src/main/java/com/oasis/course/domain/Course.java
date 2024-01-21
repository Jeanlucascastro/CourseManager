package com.oasis.course.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Table(name = "course")
@Entity(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted = false")
public class Course extends GenericEntity {

    @Column
    private String name;

    @Column
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Video> videos = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
