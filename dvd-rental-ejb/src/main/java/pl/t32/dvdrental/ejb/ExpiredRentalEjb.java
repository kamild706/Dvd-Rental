package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.ejb.dao.DvdDao;
import pl.t32.dvdrental.ejb.dao.DvdRentalDao;
import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.DvdState;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class ExpiredRentalEjb {

    @EJB
    private MailSender mailSender;

    @EJB
    private DvdRentalDao dao;

    @EJB
    private DvdDao dvdDao;

    public static final Logger logger = Logger.getLogger(ExpiredRentalEjb.class.getName());

    @Schedule(hour = "*", minute="*", persistent = false)
    public void checkExpiredRentals() {
        logger.info("Looking for expired rentals. Service started by timer service");
        List<DvdRental> rentals = dao.getExpiredRentals();
        for (DvdRental rental : rentals) {
            if (rental.getDvd().getState() == DvdState.AVAILABLE) {
                cancelRental(rental);
                sendConfirmation(rental);
            }
        }
    }

    private void cancelRental(DvdRental rental) {
        Dvd dvd = rental.getDvd();
        dvd.removeRental(rental);
        dao.remove(rental.getId());
        dvdDao.update(dvd);
    }

    private void sendConfirmation(DvdRental rental) {
        String email = rental.getCustomer().getEmail();
        String message =
                "Twoja rezerwacja na film " + rental.getDvd().getTitle() + " została anulowana z powodu nieodebrania filmu";
        String title = "Rezerwacja anulowana";

        mailSender.sendMail(email, title, message);
    }
}
