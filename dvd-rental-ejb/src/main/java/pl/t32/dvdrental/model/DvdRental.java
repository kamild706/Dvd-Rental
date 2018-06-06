package pl.t32.dvdrental.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "DvdRental.findByUser", query = "select d from DvdRental d where d.customer = :customer"),
        @NamedQuery(name = "DvdRental.getPendingRentals",
                query = "select d from DvdRental d where d.dvd = :dvd and (d.rentedSince > :now or d.rentedTo > :now)"),
        @NamedQuery(name = "DvdRental.getExpiredRentals",
                query = "select d from DvdRental d where d.rentedSince < :date and d.state = pl.t32.dvdrental.model.RentalState.RESERVED")
})

public class DvdRental implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime rentedSince;

    private LocalDateTime rentedTo;

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

    public LocalDateTime getRentedSince() {
        return rentedSince;
    }

    public void setRentedSince(LocalDateTime rentedSince) {
        this.rentedSince = rentedSince;
    }

    public LocalDateTime getRentedTo() {
        return rentedTo;
    }

    public void setRentedTo(LocalDateTime rentedTo) {
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

    public boolean dateInRentalPeriod(LocalDateTime date) {
        return rentedSince.isBefore(date) && rentedTo.isAfter(date);
    }

    @Override
    public String toString() {
        return "DvdRental{" +
                "id=" + id +
                ", rentedSince=" + rentedSince +
                ", rentedTo=" + rentedTo +
                ", dvd=" + dvd +
                ", customer=" + customer +
                ", state=" + state +
                '}';
    }
}
