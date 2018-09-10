package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MeasureDTO {


    private Long id;
    private Long validProtocolId;
    private Double iAdjust;
    private Double uAdjust;
    private Double iPower;
    private Double uPower;
    private Double iValid;
    private Double uValid;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValidProtocolId() {
        return validProtocolId;
    }

    public void setValidProtocolId(Long validProtocolId) {
        this.validProtocolId = validProtocolId;
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
        MeasureDTO that = (MeasureDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(validProtocolId, that.validProtocolId) &&
                Objects.equals(iAdjust, that.iAdjust) &&
                Objects.equals(uAdjust, that.uAdjust) &&
                Objects.equals(iPower, that.iPower) &&
                Objects.equals(uPower, that.uPower) &&
                Objects.equals(iValid, that.iValid) &&
                Objects.equals(uValid, that.uValid) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, validProtocolId, iAdjust, uAdjust, iPower, uPower, iValid, uValid, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "MeasureDTO{" +
                "id=" + id +
                ", validProtocolId=" + validProtocolId +
                ", iAdjust=" + iAdjust +
                ", uAdjust=" + uAdjust +
                ", iPower=" + iPower +
                ", uPower=" + uPower +
                ", iValid=" + iValid +
                ", uValid=" + uValid +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }
}
