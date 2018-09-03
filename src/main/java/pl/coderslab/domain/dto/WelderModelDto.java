package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class WelderModelDto {

    private Long id;
    private String name;
    private BrandDto brand;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean plazma;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private Double migImin;
    private Double migImax;
    private Double migUmin;
    private Double migUmax;
    private Double mmaImin;
    private Double mmaImax;
    private Double mmaUmin;
    private Double mmaUmax;
    private Double tigImin;
    private Double tigImax;
    private Double tigUmin;
    private Double tigUmax;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
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

    public Double getMigImin() {
        return migImin;
    }

    public void setMigImin(Double migImin) {
        this.migImin = migImin;
    }

    public Double getMigImax() {
        return migImax;
    }

    public void setMigImax(Double migImax) {
        this.migImax = migImax;
    }

    public Double getMigUmin() {
        return migUmin;
    }

    public void setMigUmin(Double migUmin) {
        this.migUmin = migUmin;
    }

    public Double getMigUmax() {
        return migUmax;
    }

    public void setMigUmax(Double migUmax) {
        this.migUmax = migUmax;
    }

    public Double getMmaImin() {
        return mmaImin;
    }

    public void setMmaImin(Double mmaImin) {
        this.mmaImin = mmaImin;
    }

    public Double getMmaImax() {
        return mmaImax;
    }

    public void setMmaImax(Double mmaImax) {
        this.mmaImax = mmaImax;
    }

    public Double getMmaUmin() {
        return mmaUmin;
    }

    public void setMmaUmin(Double mmaUmin) {
        this.mmaUmin = mmaUmin;
    }

    public Double getMmaUmax() {
        return mmaUmax;
    }

    public void setMmaUmax(Double mmaUmax) {
        this.mmaUmax = mmaUmax;
    }

    public Double getTigImin() {
        return tigImin;
    }

    public void setTigImin(Double tigImin) {
        this.tigImin = tigImin;
    }

    public Double getTigImax() {
        return tigImax;
    }

    public void setTigImax(Double tigImax) {
        this.tigImax = tigImax;
    }

    public Double getTigUmin() {
        return tigUmin;
    }

    public void setTigUmin(Double tigUmin) {
        this.tigUmin = tigUmin;
    }

    public Double getTigUmax() {
        return tigUmax;
    }

    public void setTigUmax(Double tigUmax) {
        this.tigUmax = tigUmax;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WelderModelDto that = (WelderModelDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(mig, that.mig) &&
                Objects.equals(mma, that.mma) &&
                Objects.equals(tig, that.tig) &&
                Objects.equals(plazma, that.plazma) &&
                Objects.equals(currentMeter, that.currentMeter) &&
                Objects.equals(voltageMeter, that.voltageMeter) &&
                Objects.equals(migImin, that.migImin) &&
                Objects.equals(migImax, that.migImax) &&
                Objects.equals(migUmin, that.migUmin) &&
                Objects.equals(migUmax, that.migUmax) &&
                Objects.equals(mmaImin, that.mmaImin) &&
                Objects.equals(mmaImax, that.mmaImax) &&
                Objects.equals(mmaUmin, that.mmaUmin) &&
                Objects.equals(mmaUmax, that.mmaUmax) &&
                Objects.equals(tigImin, that.tigImin) &&
                Objects.equals(tigImax, that.tigImax) &&
                Objects.equals(tigUmin, that.tigUmin) &&
                Objects.equals(tigUmax, that.tigUmax) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, brand, mig, mma, tig, plazma, currentMeter, voltageMeter, migImin, migImax, migUmin, migUmax, mmaImin, mmaImax, mmaUmin, mmaUmax, tigImin, tigImax, tigUmin, tigUmax, creationDate, modificationDate, versionId);
    }
}