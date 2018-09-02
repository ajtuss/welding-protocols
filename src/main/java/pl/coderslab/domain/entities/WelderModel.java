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

    @OneToOne(cascade = CascadeType.REMOVE)
    private Parameter migParameter;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Parameter mmaParameter;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Parameter tigParameter;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Parameter plazmaParameter;

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

    public Parameter getMigParameter() {
        return migParameter;
    }

    public void setMigParameter(Parameter migParameter) {
        this.migParameter = migParameter;
    }

    public Parameter getMmaParameter() {
        return mmaParameter;
    }

    public void setMmaParameter(Parameter mmaParameter) {
        this.mmaParameter = mmaParameter;
    }

    public Parameter getTigParameter() {
        return tigParameter;
    }

    public void setTigParameter(Parameter tigParameter) {
        this.tigParameter = tigParameter;
    }

    public Parameter getPlazmaParameter() {
        return plazmaParameter;
    }

    public void setPlazmaParameter(Parameter plazmaParameter) {
        this.plazmaParameter = plazmaParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WelderModel that = (WelderModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(mig, that.mig) &&
                Objects.equals(mma, that.mma) &&
                Objects.equals(tig, that.tig) &&
                Objects.equals(plazma, that.plazma) &&
                Objects.equals(currentMeter, that.currentMeter) &&
                Objects.equals(voltageMeter, that.voltageMeter) &&
                Objects.equals(migParameter, that.migParameter) &&
                Objects.equals(mmaParameter, that.mmaParameter) &&
                Objects.equals(tigParameter, that.tigParameter) &&
                Objects.equals(plazmaParameter, that.plazmaParameter);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, brand, mig, mma, tig, plazma, currentMeter, voltageMeter, migParameter, mmaParameter, tigParameter, plazmaParameter);
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
                ", migParameter=" + migParameter +
                ", mmaParameter=" + mmaParameter +
                ", tigParameter=" + tigParameter +
                ", plazmaParameter=" + plazmaParameter +
                "} " + super.toString();
    }
}
