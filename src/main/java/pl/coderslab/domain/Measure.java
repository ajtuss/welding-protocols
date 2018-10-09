package pl.coderslab.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "measures")
public class Measure extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validprotocol_id")
    private ValidProtocol validProtocol;

    @Column(precision = 4, scale = 0)
    private BigDecimal iAdjust;

    @Column(precision = 4, scale = 1)
    private BigDecimal uAdjust;

    @Column(precision = 4, scale = 0)
    private BigDecimal iPower;

    @Column(precision = 4, scale = 1)
    private BigDecimal uPower;

    @Column(precision = 4, scale = 0)
    private BigDecimal iValid;

    @Column(precision = 4, scale = 1)
    private BigDecimal uValid;

    @Column(precision = 4, scale = 1)
    private BigDecimal iError;

    @Column(precision = 4, scale = 1)
    private BigDecimal uError;

    private boolean iResult;
    private boolean uResult;
}
