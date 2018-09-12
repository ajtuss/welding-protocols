package pl.coderslab.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ranges")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Range {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return Objects.equals(id, range.id) &&
                Objects.equals(iMin, range.iMin) &&
                Objects.equals(iMax, range.iMax) &&
                Objects.equals(uMin, range.uMin) &&
                Objects.equals(uMax, range.uMax);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, iMin, iMax, uMin, uMax);
    }

    @Override
    public String toString() {
        return "Range{" +
                "id=" + id +
                ", iMin=" + iMin +
                ", iMax=" + iMax +
                ", uMin=" + uMin +
                ", uMax=" + uMax +
                '}';
    }
}
