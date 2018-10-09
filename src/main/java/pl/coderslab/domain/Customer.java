package pl.coderslab.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer extends AbstractEntity {

    @Column(unique = true)
    private String shortName;

    private String fullName;

    private String city;

    private String zip;

    private String street;

    @Email
    private String email;

    @Column(unique = true)
    private String nip;

    @OneToMany(mappedBy = "customer")
    private List<Machine> machines = new ArrayList<>();

    public void addMachine(Machine machine) {
        machines.add(machine);
        machine.setCustomer(this);
    }

    public void removeMachine(Machine machine) {
        machines.remove(machine);
        machine.setCustomer(null);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(shortName, customer.shortName) &&
                Objects.equals(fullName, customer.fullName) &&
                Objects.equals(city, customer.city) &&
                Objects.equals(zip, customer.zip) &&
                Objects.equals(street, customer.street) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(nip, customer.nip);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), shortName, fullName, city, zip, street, email, nip);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", street='" + street + '\'' +
                ", email='" + email + '\'' +
                ", nip='" + nip + '\'' +
                "} " + super.toString();
    }
}
