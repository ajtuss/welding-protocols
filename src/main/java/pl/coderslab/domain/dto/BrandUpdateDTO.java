package pl.coderslab.domain.dto;

import lombok.Data;

@Data
public class BrandUpdateDTO {

    private Long id;
    private String name;
    private Long versionId;
}
