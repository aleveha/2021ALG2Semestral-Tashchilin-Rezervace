package app;

import java.util.Properties;

public class OutlookEmailSender extends EmailSender {
    private static final String emailFrom = "covid_19_test_reservation@outlook.com";
    private static final String password = "21022001qQ";
    private static final Properties properties = getProperties();

    /**
     * Setting email sending properties
     * @return email sending properties
     */
    private static Properties getProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");

        return props;
    }

    /**
     * OutlookEmailSender constructor
     */
    public OutlookEmailSender() {
        super(emailFrom, password, properties);
    }
}
