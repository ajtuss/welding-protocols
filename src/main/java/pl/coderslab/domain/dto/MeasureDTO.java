package pl.coderslab.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasureDTO {


    private Long id;
    private Long validProtocolId;
    private BigDecimal iAdjust;
    private BigDecimal uAdjust;
    private BigDecimal iPower;
    private BigDecimal uPower;
    private BigDecimal iValid;
    private BigDecimal uValid;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

}
