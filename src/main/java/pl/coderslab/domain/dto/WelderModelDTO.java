package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class WelderModelDTO {

    private Long id;
    private String name;
    private Long brandId;
    private Boolean mig;
    private Boolean mma;
    private Boolean tig;
    private Boolean plazma;
    private Boolean currentMeter;
    private Boolean voltageMeter;
    private Double iMin;
    private Double iMax;
    private Double uMin;
    private Double uMax;
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


    public Long getBrandId() {

        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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
                Objects.equals(brandId, that.brandId) &&
                Objects.equals(mig, that.mig) &&
                Objects.equals(mma, that.mma) &&
                Objects.equals(tig, that.tig) &&
                Objects.equals(plazma, that.plazma) &&
                Objects.equals(currentMeter, that.currentMeter) &&
                Objects.equals(voltageMeter, that.voltageMeter) &&
                Objects.equals(iMin, that.iMin) &&
                Objects.equals(iMax, that.iMax) &&
                Objects.equals(uMin, that.uMin) &&
                Objects.equals(uMax, that.uMax) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, brandId, mig, mma, tig, plazma, currentMeter, voltageMeter, iMin, iMax, uMin, uMax, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "WelderModelDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brandId=" + brandId +
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
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }

}
