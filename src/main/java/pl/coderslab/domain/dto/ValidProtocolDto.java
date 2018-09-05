package pl.coderslab.domain.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ValidProtocolDto {

    private Long id;
    private Long machineId;
    private String protocolNumber;
    private String machineSerialNumber;
    private String machineInwNumber;
    private Long machineWelderModelId;
    private String machineWelderModelName;
    private Long machineWelderModelBrandId;
    private String machineWelderModelBrandName;
    private Long machineCustomerId;
    private String machineCustomerShortName;
    private LocalDateTime dateValidation;
    private LocalDateTime nextValidation;
    private Boolean result;
    private Boolean finalized;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long versionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    public String getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public void setMachineSerialNumber(String machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public String getMachineInwNumber() {
        return machineInwNumber;
    }

    public void setMachineInwNumber(String machineInwNumber) {
        this.machineInwNumber = machineInwNumber;
    }

    public Long getMachineWelderModelId() {
        return machineWelderModelId;
    }

    public void setMachineWelderModelId(Long machineWelderModelId) {
        this.machineWelderModelId = machineWelderModelId;
    }

    public String getMachineWelderModelName() {
        return machineWelderModelName;
    }

    public void setMachineWelderModelName(String machineWelderModelName) {
        this.machineWelderModelName = machineWelderModelName;
    }

    public Long getMachineWelderModelBrandId() {
        return machineWelderModelBrandId;
    }

    public void setMachineWelderModelBrandId(Long machineWelderModelBrandId) {
        this.machineWelderModelBrandId = machineWelderModelBrandId;
    }

    public String getMachineWelderModelBrandName() {
        return machineWelderModelBrandName;
    }

    public void setMachineWelderModelBrandName(String machineWelderModelBrandName) {
        this.machineWelderModelBrandName = machineWelderModelBrandName;
    }

    public Long getMachineCustomerId() {
        return machineCustomerId;
    }

    public void setMachineCustomerId(Long machineCustomerId) {
        this.machineCustomerId = machineCustomerId;
    }

    public String getMachineCustomerShortName() {
        return machineCustomerShortName;
    }

    public void setMachineCustomerShortName(String machineCustomerShortName) {
        this.machineCustomerShortName = machineCustomerShortName;
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
        ValidProtocolDto that = (ValidProtocolDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(protocolNumber, that.protocolNumber) &&
                Objects.equals(machineSerialNumber, that.machineSerialNumber) &&
                Objects.equals(machineInwNumber, that.machineInwNumber) &&
                Objects.equals(machineWelderModelId, that.machineWelderModelId) &&
                Objects.equals(machineWelderModelName, that.machineWelderModelName) &&
                Objects.equals(machineWelderModelBrandId, that.machineWelderModelBrandId) &&
                Objects.equals(machineWelderModelBrandName, that.machineWelderModelBrandName) &&
                Objects.equals(machineCustomerId, that.machineCustomerId) &&
                Objects.equals(machineCustomerShortName, that.machineCustomerShortName) &&
                Objects.equals(dateValidation, that.dateValidation) &&
                Objects.equals(nextValidation, that.nextValidation) &&
                Objects.equals(result, that.result) &&
                Objects.equals(finalized, that.finalized) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(modificationDate, that.modificationDate) &&
                Objects.equals(versionId, that.versionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, protocolNumber, machineSerialNumber, machineInwNumber, machineWelderModelId, machineWelderModelName, machineWelderModelBrandId, machineWelderModelBrandName, machineCustomerId, machineCustomerShortName, dateValidation, nextValidation, result, finalized, creationDate, modificationDate, versionId);
    }

    @Override
    public String toString() {
        return "ValidProtocolDto{" +
                "id=" + id +
                ", protocolNumber='" + protocolNumber + '\'' +
                ", machineSerialNumber='" + machineSerialNumber + '\'' +
                ", machineInwNumber='" + machineInwNumber + '\'' +
                ", machineWelderModelId=" + machineWelderModelId +
                ", machineWelderModelName='" + machineWelderModelName + '\'' +
                ", machineWelderModelBrandId=" + machineWelderModelBrandId +
                ", machineWelderModelBrandName='" + machineWelderModelBrandName + '\'' +
                ", machineCustomerId=" + machineCustomerId +
                ", machineCustomerShortName='" + machineCustomerShortName + '\'' +
                ", dateValidation=" + dateValidation +
                ", nextValidation=" + nextValidation +
                ", result=" + result +
                ", finalized=" + finalized +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", versionId=" + versionId +
                '}';
    }
}
