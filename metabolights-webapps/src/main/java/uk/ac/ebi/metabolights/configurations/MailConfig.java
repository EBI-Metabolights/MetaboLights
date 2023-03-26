package uk.ac.ebi.metabolights.configurations;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        JndiTemplate jndi = new JndiTemplate();
        try {
            String mailServer = jndi.lookup("java:comp/env/mailServer", java.lang.String.class);
            int mailServerPort = jndi.lookup("java:comp/env/mailServerPort", java.lang.Integer.class);
            sender.setHost(mailServer);
            sender.setPort(mailServerPort);
        } catch (NamingException e) {
            throw new RuntimeException("", e);
        }
        
        return sender;
    }
}
