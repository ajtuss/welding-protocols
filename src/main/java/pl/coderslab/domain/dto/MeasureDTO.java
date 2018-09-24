package pl.coderslab.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(value = "measure", collectionRelation = "measures")
public class MeasureDTO {


    private Long id;
    private Long validProtocolId;

    @DecimalMax("1000")
    private BigDecimal iAdjust;

    private BigDecimal uAdjust;
    private BigDecimal iPower;
    private BigDecimal uPower;
    private BigDecimal iValid;
    private BigDecimal uValid;
    private BigDecimal iError;
    private BigDecimal uError;
    private boolean iResult;
    private boolean uResult;

    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}
