package com.example.everything.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final JavaMailSender mailSender;

    @Value("${portfolio.mail.to}")
    private String mailTo;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public HomeController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/resume")
    public String resume() {
        return "resume";
    }

    @GetMapping("/teaching-assistant")
    public String ta() {
        return "ta";
    }

   @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    // ---------- Contact Form Submit ----------

    @PostMapping("/contact")
    public String submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailTo);
        mail.setFrom(mailFrom);
        mail.setSubject("Portfolio Contact Form: " + name);
        mail.setText(
                "You received a new message from your portfolio.\n\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n\n" +
                "Message:\n" + message
        );

        mailSender.send(mail);

        return "contact-success";
    }
}