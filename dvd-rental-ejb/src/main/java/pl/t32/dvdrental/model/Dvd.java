package pl.t32.dvdrental.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Entity
@NamedQueries({
        @NamedQuery(name = "Dvd.findAll", query = "select d from Dvd d"),
//        @NamedQuery(name = "Dvd.isAvailable", query = "")
})
public class Dvd implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String director;
    private String description;

    @OneToMany(mappedBy="dvd", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<DvdRental> rentals = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String author) {
        this.director = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public List<DvdRental> getRentals() {
        return rentals;
    }

    public void addRental(DvdRental rental) {
        rentals.add(rental);
    }

    public void removeRental(DvdRental rental) {
        rentals.remove(rental);
    }

    public DvdState getState() {
        boolean isReserved = false;
        boolean isRented = false;

        Logger logger = Logger.getLogger(Dvd.class.getName());
        logger.info("Rentals size: " + rentals.size());
        for (DvdRental rental : rentals) {
            logger.info("Rental state: " + rental.getState());
            if (rental.getState() == RentalState.RENTED) {
                isRented = true;
            }
            else if (rental.getState() == RentalState.RESERVED) {
                isReserved = true;
            }
        }

        if (isRented && isReserved)
            return DvdState.RENTED_AND_RESERVED;
        if (isRented)
            return DvdState.RENTED;
        if (isReserved)
            return DvdState.RESERVED;
        return DvdState.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Dvd{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + director + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
