package pl.coderslab.domain.dto;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Relation(value = "brand", collectionRelation = "brands")
public class BrandDTO {

    private Long id;
    @NotNull
    @Size(min = 3, max = 30)
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
