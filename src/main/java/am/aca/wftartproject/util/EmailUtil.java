package am.aca.wftartproject.util;

import am.aca.wftartproject.exception.service.InvalidEntryException;
import am.aca.wftartproject.model.AbstractUser;

import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author surik
 */
public class EmailUtil {
    public static void sendEmail(AbstractUser abstractUser, String subject, String mailText) {
        String EMAIL = "workfront.internship@gmail.com";
        String PASSWORD = "project2017";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });

        try {
            // Creating MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // Setting sender address
            message.setFrom(new InternetAddress(EMAIL));
            // Adding receiver
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(abstractUser.getEmail()));
            // Adding subject
            message.setSubject(subject);
            // Adding message
            message.setText(mailText);
            // Sending email
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
