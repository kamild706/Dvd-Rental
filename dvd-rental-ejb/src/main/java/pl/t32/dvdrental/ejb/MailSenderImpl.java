package pl.t32.dvdrental.ejb;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

@Stateless
public class MailSenderImpl implements MailSender {

    private static final Logger logger = Logger.getLogger(MailSenderImpl.class.getName());

    @Asynchronous
    public void sendMail(String to, String subject, String body) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "poczta.o2.pl");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(props);
        MimeMessage m = new MimeMessage(session);
        try {
            InternetAddress[] address = {new InternetAddress(to)};
            m.setFrom(new InternetAddress("konto_na_projekt@o2.pl"));
            m.setRecipients(Message.RecipientType.TO, address);
            m.setSubject(subject);
            m.setSentDate(new Date());
            m.setText(body);
            Transport.send(m, "konto_na_projekt", "!q2w3e4r5t6y");

            logger.fine("Confirmation email to " + to + " has been sent");
        } catch (MessagingException e) {
            logger.warning("Confirmation email couldn't be sent to " + to + ":\n" + e.getMessage());
        }
    }
}
