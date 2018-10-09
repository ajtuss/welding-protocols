package pl.coderslab.service.dto;

import lombok.Data;
import org.springframework.hateoas.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Relation(value = "model", collectionRelation = "models")
public class WelderModelDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    @NotNull
    private Long brandId;
    private String brandName;
    @NotNull
    private Boolean mig;
    @NotNull
    private Boolean mma;
    @NotNull
    private Boolean tig;
    @NotNull
    private Boolean currentMeter;
    @NotNull
    private Boolean voltageMeter;
    @NotNull
    private Boolean stepControl;
    @Valid
    private RangeDTO migRange;
    @Valid
    private RangeDTO mmaRange;
    @Valid
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