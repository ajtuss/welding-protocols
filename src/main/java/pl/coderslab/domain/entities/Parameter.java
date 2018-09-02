package pl.coderslab.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "parameters")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 4, scale = 1)
    private Double iMin;

    @Column(precision = 4, scale = 1)
    private Double iMax;

    @Column(precision = 5, scale = 2)
    private Double uMin;

    @Column(precision = 5, scale = 2)
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
        Parameter parameter = (Parameter) o;
        return Objects.equals(id, parameter.id) &&
                Objects.equals(iMin, parameter.iMin) &&
                Objects.equals(iMax, parameter.iMax) &&
                Objects.equals(uMin, parameter.uMin) &&
                Objects.equals(uMax, parameter.uMax);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, iMin, iMax, uMin, uMax);
    }
}
