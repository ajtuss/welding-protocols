package pl.coderslab.domain.dto;

import java.util.Objects;

public class ParameterDto {

    private Long id;
    private Double iMin;
    private Double iMax;
    private Double uMin;
    private Double uMax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        ParameterDto that = (ParameterDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(iMin, that.iMin) &&
                Objects.equals(iMax, that.iMax) &&
                Objects.equals(uMin, that.uMin) &&
                Objects.equals(uMax, that.uMax);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, iMin, iMax, uMin, uMax);
    }

    @Override
    public String toString() {
        return "ParameterDto{" +
                "id=" + id +
                ", iMin=" + iMin +
                ", iMax=" + iMax +
                ", uMin=" + uMin +
                ", uMax=" + uMax +
                '}';
    }
}
