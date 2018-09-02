package pl.coderslab.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "weldermodels")
public class WelderModel extends AbstractEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private Boolean mig;

    private Boolean mma;

    private Boolean tig;

    private Boolean plazma;

    private Boolean currentMeter;

    private Boolean voltageMeter;

    @Column(precision = 4, scale = 1)
    private Double iMin;

    @Column(precision = 4, scale = 1)
    private Double iMax;

    @Column(precision = 5, scale = 2)
    private Double uMin;

    @Column(precision = 5, scale = 2)
    private Double uMax;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Boolean getMig() {
        return mig;
    }

    public void setMig(Boolean mig) {
        this.mig = mig;
    }

    public Boolean getMma() {
        return mma;
    }

    public void setMma(Boolean mma) {
        this.mma = mma;
    }

    public Boolean getTig() {
        return tig;
    }

    public void setTig(Boolean tig) {
        this.tig = tig;
    }

    public Boolean getPlazma() {
        return plazma;
    }

    public void setPlazma(Boolean plazma) {
        this.plazma = plazma;
    }

    public Boolean getCurrentMeter() {
        return currentMeter;
    }

    public void setCurrentMeter(Boolean currentMeter) {
        this.currentMeter = currentMeter;
    }

    public Boolean getVoltageMeter() {
        return voltageMeter;
    }

    public void setVoltageMeter(Boolean voltageMeter) {
        this.voltageMeter = voltageMeter;
    }

    public Double getiMin() {
        return iMin;
    }

    public void setiMin(Double iMin) {
        this.iMin = iMin;
    }

    public Double getiMax() {
        return iMax;
    }

    public void setiMax(Double iMax) {
        this.iMax = iMax;
    }

    public Double getuMin() {
        return uMin;
    }

    public void setuMin(Double uMin) {
        this.uMin = uMin;
    }

    public Double getuMax() {
        return uMax;
    }

    public void setuMax(Double uMax) {
        this.uMax = uMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WelderModel that = (WelderModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(mig, that.mig) &&
                Objects.equals(mma, that.mma) &&
                Objects.equals(tig, that.tig) &&
                Objects.equals(plazma, that.plazma) &&
                Objects.equals(currentMeter, that.currentMeter) &&
                Objects.equals(voltageMeter, that.voltageMeter) &&
                Objects.equals(iMin, that.iMin) &&
                Objects.equals(iMax, that.iMax) &&
                Objects.equals(uMin, that.uMin) &&
                Objects.equals(uMax, that.uMax);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, mig, mma, tig, plazma, currentMeter, voltageMeter, iMin, iMax, uMin, uMax);
    }

    @Override
    public String toString() {
        return "WelderModel{" +
                "name='" + name + '\'' +
                ", brand=" + brand +
                ", mig=" + mig +
                ", mma=" + mma +
                ", tig=" + tig +
                ", plazma=" + plazma +
                ", currentMeter=" + currentMeter +
                ", voltageMeter=" + voltageMeter +
                ", iMin=" + iMin +
                ", iMax=" + iMax +
                ", uMin=" + uMin +
                ", uMax=" + uMax +
                "} " + super.toString();
    }
}
