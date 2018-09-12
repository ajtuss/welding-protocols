package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(value = "model", collectionRelation = "models")
public class WelderModelDTO {

    private Long id;
    private String name;
    private Long brandId;
    private String brandName;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private Boolean stepControl;
    private RangeDTO migRange;
    private RangeDTO mmaRange;
    private RangeDTO tigRange;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}