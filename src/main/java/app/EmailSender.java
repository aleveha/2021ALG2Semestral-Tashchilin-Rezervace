package app;

import core.IEmailSender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender implements IEmailSender {
    private final String emailFrom;
    private final String password;
    private final String emailTo;
    private final String title;
    private final String message;
    private final Properties properties;

    /**
     * EmailSender constructor
     * @param emailFrom sender email / email account login
     * @param password email account password
     * @param emailTo recipient's email
     * @param title email title
     * @param message email message
     * @param properties email sending properties
     */
    public EmailSender(String emailFrom, String password, String emailTo, String title, String message, Properties properties) {
        this.emailFrom = emailFrom;
        this.password = password;
        this.emailTo = emailTo;
        this.title = title;
        this.message = message;
        this.properties = properties;
    }

    /**
     * Sending email this reservation info to user
     */
    @Override
    public void sendEmail() {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            msg.setSubject(title);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (MessagingException messagingException) {
            throw new RuntimeException("Error occurred while sending a message!");
        }
    }
}
