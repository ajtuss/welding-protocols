package pl.coderslab.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandDTO {

    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}
