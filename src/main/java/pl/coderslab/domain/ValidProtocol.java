package pl.coderslab.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "validations")
@Data
public class ValidProtocol extends AbstractEntity {

    @Column(unique = true)
    private String protocolNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @OneToMany(mappedBy = "validProtocol",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Measure> measures = new ArrayList<>();

    private Boolean result;
    private boolean finalized;
    private LocalDateTime dateValidation;
    private LocalDateTime nextValidation;

    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    private PowerType type;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "protocol_id")
    private DBFile protocol;

    public void addMeasure(Measure measure) {
        measures.add(measure);
        measure.setValidProtocol(this);
    }

    public void removeMeasure(Measure measure) {
        measures.remove(measure);
        measure.setValidProtocol(null);
    }

}
