package com.oasis.course.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    private String name;
    private String url;
    private Long ordering;
    private Long courseId;
    private Long companyId;

}
