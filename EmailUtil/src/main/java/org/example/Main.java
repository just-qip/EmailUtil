package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("EMail util started!");
        try {
            if (args.length < 2) {
                System.err.println("Usage: java org.example.Main <toEmail> [<subject>] <body> [<fromPersonal>]");
                System.err.println("  - with 2 args: toEmail and body (subject defaults to 'SPDP notify')");
                System.err.println("  - with 3 args: toEmail, subject, body");
                System.err.println("  - with 4 args: toEmail, subject, body, fromPersonal");
                return;
            }

            String toEmail = args[0];
            String subject;
            String body;
            String fromPersonal = null; // по умолчанию null

            if (args.length == 2) {
                subject = "SPDP notify";
                body = args[1];
            } else if (args.length == 3) {
                subject = args[1];
                body = args[2];
            } else { // args.length >= 4
                subject = args[1];
                body = args[2];
                fromPersonal = args[3];
            }

            // Настройки SMTP (как в оригинале)
            String smtpHost = "gw.alfaintra.net";
            String smtpPort = "25";
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            Session session = Session.getInstance(props, null);

            sendEmail(session, toEmail, subject, body, fromPersonal);
            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("EMail util ended!");
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body, String fromPersonal) throws Exception {
        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        // Создание адреса отправителя с учётом personal
        InternetAddress fromAddress;
        if (fromPersonal != null && !fromPersonal.trim().isEmpty()) {
            fromAddress = new InternetAddress("noreply@alfa-bank.info", fromPersonal);
        } else {
            fromAddress = new InternetAddress("noreply@alfa-bank.info");
        }
        msg.setFrom(fromAddress);

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(body, "text/html; charset=utf-8");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}