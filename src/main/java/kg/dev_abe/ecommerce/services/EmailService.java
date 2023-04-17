package kg.dev_abe.ecommerce.services;

import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final Transport transport;
    private final MimeMessage mimeMessage;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Async
    public void sendEmail(String email, String subject, String body)  {
        try (transport) {

            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, email);
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);
            mimeMessage.setSentDate(new java.util.Date());

            transport.connect(username, password);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
        log.info("Send data email");
    }
}
