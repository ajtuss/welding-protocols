package pl.coderslab.domain.entities;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "validations")
public class ValidProtocol extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @NaturalId
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
    private Boolean finalized;
    private LocalDateTime dateValidation;
    private LocalDateTime nextValidation;

    public void addMeasure(Measure measure){
        measures.add(measure);
        measure.setValidProtocol(this);
    }

    public void removeMeasure(Measure measure){
        measures.remove(measure);
        measure.setValidProtocol(null);
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Boolean getFinalized() {
        return finalized;
    }

    public void setFinalized(Boolean finalized) {
        this.finalized = finalized;
    }

    public LocalDateTime getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public LocalDateTime getNextValidation() {
        return nextValidation;
    }

    public void setNextValidation(LocalDateTime nextValidation) {
        this.nextValidation = nextValidation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ValidProtocol that = (ValidProtocol) o;
        return Objects.equals(protocolNumber, that.protocolNumber) &&
                Objects.equals(result, that.result) &&
                Objects.equals(finalized, that.finalized);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), protocolNumber, result, finalized);
    }

    @Override
    public String toString() {
        return "ValidProtocol{" +
                "protocolNumber='" + protocolNumber + '\'' +
                ", result=" + result +
                ", finalized=" + finalized +
                "} " + super.toString();
    }
}
