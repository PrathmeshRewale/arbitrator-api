package com.mac.arbitrator.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailUtil {
    private static JavaMailSender mailSender;

    // Private constructor to prevent instantiation
    private MailUtil() {}

    /**
     * Returns a singleton JavaMailSender instance configured with the provided properties.
     * If already initialized, updates properties dynamically.
     */
    public static synchronized JavaMailSender getMailSender(
            String host,
            int port,
            String username,
            String password
    ) {
        if (mailSender == null) {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(host);
            sender.setPort(port);
            sender.setUsername(username);
            sender.setPassword(password);

            Properties props = sender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "false");

            mailSender = sender;
        } else {
            // Update existing instance dynamically if needed
            JavaMailSenderImpl sender = (JavaMailSenderImpl) mailSender;
            sender.setHost(host);
            sender.setPort(port);
            sender.setUsername(username);
            sender.setPassword(password);
        }

        return mailSender;
    }
}
