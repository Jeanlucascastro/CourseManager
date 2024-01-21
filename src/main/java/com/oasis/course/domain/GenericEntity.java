package com.oasis.course.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private boolean deleted;

    @Column()
    private boolean ativo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_update")
    private LocalDateTime dateUpdate;



    @PrePersist
    private void fillDate() {
        this.setDateCreate(LocalDateTime.now());
    }

    @PreUpdate
    private void updateDate() {
        this.setDateUpdate(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "GenericEntity{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", ativo=" + ativo +
                ", dateCreate=" + dateCreate +
                ", dateUpdate=" + dateUpdate +
                '}';
    }
}
