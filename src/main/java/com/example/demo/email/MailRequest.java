package com.example.demo.email;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private String toEmail;
    private String subject;
    private String body;
}
