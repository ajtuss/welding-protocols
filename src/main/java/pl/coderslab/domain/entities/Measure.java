package pl.coderslab.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "measuers")
public class Measure extends AbstractEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "validprotocol_id")
        private ValidProtocol validProtocol;
        private Double iAdjust;
        private Double uAdjust;
        private Double iPower;
        private Double uPower;
        private Double iValid;
        private Double uValid;

    public ValidProtocol getValidProtocol() {
        return validProtocol;
    }

    public void setValidProtocol(ValidProtocol validProtocol) {
        this.validProtocol = validProtocol;
    }

    public Double getiAdjust() {
        return iAdjust;
    }

    public void setiAdjust(Double iAdjust) {
        this.iAdjust = iAdjust;
    }

    public Double getuAdjust() {
        return uAdjust;
    }

    public void setuAdjust(Double uAdjust) {
        this.uAdjust = uAdjust;
    }

    public Double getiPower() {
        return iPower;
    }

    public void setiPower(Double iPower) {
        this.iPower = iPower;
    }

    public Double getuPower() {
        return uPower;
    }

    public void setuPower(Double uPower) {
        this.uPower = uPower;
    }

    public Double getiValid() {
        return iValid;
    }

    public void setiValid(Double iValid) {
        this.iValid = iValid;
    }

    public Double getuValid() {
        return uValid;
    }

    public void setuValid(Double uValid) {
        this.uValid = uValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Measure measure = (Measure) o;
        return Objects.equals(iAdjust, measure.iAdjust) &&
                Objects.equals(uAdjust, measure.uAdjust) &&
                Objects.equals(iPower, measure.iPower) &&
                Objects.equals(uPower, measure.uPower) &&
                Objects.equals(iValid, measure.iValid) &&
                Objects.equals(uValid, measure.uValid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), iAdjust, uAdjust, iPower, uPower, iValid, uValid);
    }

    @Override
    public String toString() {
        return "Measure{" +
                "iAdjust=" + iAdjust +
                ", uAdjust=" + uAdjust +
                ", iPower=" + iPower +
                ", uPower=" + uPower +
                ", iValid=" + iValid +
                ", uValid=" + uValid +
                "} " + super.toString();
    }
}
