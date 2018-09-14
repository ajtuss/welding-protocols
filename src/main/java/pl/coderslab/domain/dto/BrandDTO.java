package pl.coderslab.domain.dto;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@Relation(value = "brand", collectionRelation = "brands")
public class BrandDTO {

    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;


    public BrandDTO() {
    }

    public BrandDTO(String name) {
        this.name = name;
    }

    public BrandDTO(Long id, String name, LocalDateTime creationDate, LocalDateTime modificationDate, Long versionId) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.versionId = versionId;
    }
}
