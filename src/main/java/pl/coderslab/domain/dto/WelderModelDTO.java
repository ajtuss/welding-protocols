package pl.coderslab.domain.dto;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
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

    public WelderModelDTO() {
    }

    public WelderModelDTO(String name, Long brandId, Boolean mig, Boolean mma, Boolean tig, Boolean currentMeter, Boolean voltageMeter, Boolean stepControl, RangeDTO migRange, RangeDTO mmaRange, RangeDTO tigRange) {
        this.name = name;
        this.brandId = brandId;
        this.mig = mig;
        this.mma = mma;
        this.tig = tig;
        this.currentMeter = currentMeter;
        this.voltageMeter = voltageMeter;
        this.stepControl = stepControl;
        this.migRange = migRange;
        this.mmaRange = mmaRange;
        this.tigRange = tigRange;
    }

    public WelderModelDTO(Long id, String name, Long brandId, Boolean mig, Boolean mma, Boolean tig, Boolean currentMeter, Boolean voltageMeter, Boolean stepControl, RangeDTO migRange, RangeDTO mmaRange, RangeDTO tigRange, Long versionId) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
        this.mig = mig;
        this.mma = mma;
        this.tig = tig;
        this.currentMeter = currentMeter;
        this.voltageMeter = voltageMeter;
        this.stepControl = stepControl;
        this.migRange = migRange;
        this.mmaRange = mmaRange;
        this.tigRange = tigRange;
        this.versionId = versionId;
    }

    public WelderModelDTO(Long id, String name, Long brandId, String brandName, Boolean mig, Boolean mma, Boolean tig, Boolean currentMeter, Boolean voltageMeter, Boolean stepControl, RangeDTO migRange, RangeDTO mmaRange, RangeDTO tigRange, LocalDateTime creationDate, LocalDateTime modificationDate, Long versionId) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
        this.brandName = brandName;
        this.mig = mig;
        this.mma = mma;
        this.tig = tig;
        this.currentMeter = currentMeter;
        this.voltageMeter = voltageMeter;
        this.stepControl = stepControl;
        this.migRange = migRange;
        this.mmaRange = mmaRange;
        this.tigRange = tigRange;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.versionId = versionId;
    }
}