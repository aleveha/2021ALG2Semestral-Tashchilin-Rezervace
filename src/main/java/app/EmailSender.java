package app;

import core.IEmailSender;
import core.ReadingFileException;
import core.Reservation;
import core.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EmailSender implements IEmailSender {
    private final String emailFrom;
    private final String password;
    private final Properties properties;

    /**
     * EmailSender constructor
     * @param emailFrom sender email / email account login
     * @param password email account password
     * @param properties email sending properties
     */
    public EmailSender(String emailFrom, String password, Properties properties) {
        this.emailFrom = emailFrom;
        this.password = password;
        this.properties = properties;
    }

    /**
     * Sending email with reservation information to user
     */
    @Override
    public void sendEmail(Reservation reservation) {
        User user = reservation.getUser();
        String title = "Covid-19 test reservation";
        String message = replaceTemplateVariables(readTemplate(), user, reservation, title);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
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

    /**
     * Reading HTML email template
     * @return HTML email template as string
     */
    private String readTemplate() {
        StringBuilder contentBuilder = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new FileReader("src/dataStore/emailTemplate.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException ex) {
            throw new ReadingFileException("Problems with reading the file.\n" + ex.getMessage());
        }

        return contentBuilder.toString();
    }

    /**
     * Creating email content
     * @param template email template
     * @param user user
     * @param reservation reservation
     * @param title email title
     * @return complete email content from email template
     */
    private String replaceTemplateVariables(String template, User user, Reservation reservation, String title) {
        return template
                .replace("$title", title)
                .replace("$name", user.getFirstName())
                .replace("$lastName", user.getLastName())
                .replace("$date", reservation.getDate())
                .replace("$time", reservation.getTime())
                .replace("$age", Integer.toString(user.getAge()));
    }
}
