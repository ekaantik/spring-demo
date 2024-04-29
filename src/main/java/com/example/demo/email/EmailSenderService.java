package com.example.demo.email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.password}")
    private String fromPassword;


    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,String subject,String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        log.info("email : {} , password : {} , toMail : {} body : {} subject : {} ",fromEmail,fromPassword,toEmail,body,subject);
        mailSender.send(message);
        System.out.println("Mail sent Successfully...");
    }

}
