package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class WelderModelDTO {

    private Long id;
    private String name;
    private BrandDto brand;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean plazma;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private ParameterDto migParameter;
    private ParameterDto mmaParameter;
    private ParameterDto tigParameter;
    private ParameterDto plazmaParameter;
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

    public ParameterDto getMigParameter() {
        return migParameter;
    }

    public void setMigParameter(ParameterDto migParameter) {
        this.migParameter = migParameter;
    }

    public ParameterDto getMmaParameter() {
        return mmaParameter;
    }

    public void setMmaParameter(ParameterDto mmaParameter) {
        this.mmaParameter = mmaParameter;
    }

    public ParameterDto getTigParameter() {
        return tigParameter;
    }

    public void setTigParameter(ParameterDto tigParameter) {
        this.tigParameter = tigParameter;
    }

    public ParameterDto getPlazmaParameter() {
        return plazmaParameter;
    }

    public void setPlazmaParameter(ParameterDto plazmaParameter) {
        this.plazmaParameter = plazmaParameter;
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
        WelderModelDTO that = (WelderModelDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
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
                Objects.equals(plazmaParameter, that.plazmaParameter) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, brand, mig, mma, tig, plazma, currentMeter, voltageMeter, migParameter, mmaParameter, tigParameter, plazmaParameter, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "WelderModelDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }

}
