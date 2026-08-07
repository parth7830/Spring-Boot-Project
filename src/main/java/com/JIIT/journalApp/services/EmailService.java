package com.JIIT.journalApp.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation
        .Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail
        .JavaMailSender;
import org.springframework.mail.javamail
        .MimeMessageHelper;
import org.springframework.scheduling.annotation
        .Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    // ─────────────────────────────────────────
    // 1. SIMPLE EMAIL — Plain Text
    // ─────────────────────────────────────────

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body) {
        log.info("Sending simple email to: {}",
                toEmail);
        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Simple email sent to: {}",
                    toEmail);

        } catch (Exception e) {
            log.error("Failed to send email: {}",
                    e.getMessage());
            throw new RuntimeException(
                    "Email send failed!", e
            );
        }
    }

    // ─────────────────────────────────────────
    // 2. HTML EMAIL — Formatted
    // ─────────────────────────────────────────

    public void sendHtmlEmail(String toEmail,
                              String subject,
                              String htmlBody)
            throws MessagingException {

        log.info("Sending HTML email to: {}", toEmail);

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true = HTML

        mailSender.send(message);
        log.info("HTML email sent to: {}", toEmail);
    }

    // ─────────────────────────────────────────
    // 3. EMAIL WITH ATTACHMENT
    // ─────────────────────────────────────────

    public void sendEmailWithAttachment(
            String toEmail,
            String subject,
            String body,
            String attachmentPath)
            throws MessagingException {

        log.info("Sending email with attachment to: {}",
                toEmail);

        MimeMessage message =
                mailSender.createMimeMessage();

        // true = multipart (for attachment)
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        // Attachment add karo
        FileSystemResource file =
                new FileSystemResource(
                        new File(attachmentPath)
                );
        helper.addAttachment(
                file.getFilename(), file
        );

        mailSender.send(message);
        log.info("Email with attachment sent to: {}",
                toEmail);
    }

    // ─────────────────────────────────────────
    // 4. MULTIPLE RECIPIENTS
    // ─────────────────────────────────────────

    public void sendToMultiple(String[] toEmails,
                               String subject,
                               String body)
            throws MessagingException {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmails);         // array of emails
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
        log.info("Email sent to {} recipients",
                toEmails.length);
    }

    // ─────────────────────────────────────────
    // 5. CC & BCC
    // ─────────────────────────────────────────

    public void sendWithCcBcc(String toEmail,
                              String ccEmail,
                              String bccEmail,
                              String subject,
                              String body)
            throws MessagingException {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setCc(ccEmail);    // CC
        helper.setBcc(bccEmail);  // BCC
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }

    // ─────────────────────────────────────────
    // 6. ASYNC EMAIL — Non Blocking ⭐
    // ─────────────────────────────────────────

    @Async   // alag thread mein chalega!
    public void sendAsyncEmail(String toEmail,
                               String subject,
                               String body) {
        log.info("Sending async email to: {}",
                toEmail);
        try {
            sendSimpleEmail(toEmail, subject, body);
            log.info("Async email sent!");
        } catch (Exception e) {
            log.error("Async email failed: {}",
                    e.getMessage());
        }
    }
}