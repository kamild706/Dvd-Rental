package pl.t32.dvdrental.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name="DvdRental.findByUser", query="select d from DvdRental d where d.customer = :customer")
public class DvdRental implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rentedSince;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rentedTo;

    @ManyToOne
    private Dvd dvd;

    @ManyToOne
    private UserCredentials customer;

    @Enumerated(EnumType.STRING)
    private RentalState state = RentalState.RESERVED;

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

    public RentalState getState() {
        return state;
    }

    public void setState(RentalState state) {
        this.state = state;
    }

    public boolean canBeIssued() {
        if (state == RentalState.RENTED || state == RentalState.RETURNED)
            return false;
        if (dvd.getState() == DvdState.AVAILABLE || dvd.getState() == DvdState.RESERVED)
            return true;
        return false;
    }

    public boolean canBeReturned() {
        return state == RentalState.RENTED;
    }
}
