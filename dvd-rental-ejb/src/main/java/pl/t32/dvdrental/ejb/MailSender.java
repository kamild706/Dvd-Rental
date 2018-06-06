package pl.t32.dvdrental.ejb;

import javax.ejb.Local;

@Local
public interface MailSender {

    void sendMail(String to, String subject, String body);
}
