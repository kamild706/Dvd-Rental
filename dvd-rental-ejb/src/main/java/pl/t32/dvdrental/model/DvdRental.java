package pl.t32.dvdrental.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name="DvdRental.findAll", query="select d from DvdRental d")
public class DvdRental implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date rentedSince;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date rentedTo;

//    private boolean isRented = false;

    @ManyToOne
    private Dvd dvd;

    @ManyToOne
    private UserCredentials customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRentedSince() {
        return rentedSince;
    }

    public void setRentedSince(Date rentedFrom) {
        this.rentedSince = rentedFrom;
    }

    public Date getRentedTo() {
        return rentedTo;
    }

    public void setRentedTo(Date rentedTo) {
        this.rentedTo = rentedTo;
    }

    public Dvd getDvd() {
        return dvd;
    }

    public void setDvd(Dvd dvd) {
        this.dvd = dvd;
    }

    public UserCredentials getCustomer() {
        return customer;
    }

    public void setCustomer(UserCredentials customer) {
        this.customer = customer;
    }
}
