package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(value = "brand", collectionRelation = "brands")
public class BrandDTO {

    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}
