package org.example;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("EMail util started!");
        try {
            // Ожидаем минимум 1 аргумент (тело письма)
            if (args.length < 1) {
                System.err.println("Usage: java org.example.Main <toEmail> [<subject>] <body>");
                System.err.println("If subject is omitted, default 'SPDP notify' is used.");
                return;
            }

            String toEmail = args[0];
            String subject;
            String body;

            if (args.length == 2) {
                // Только email и тело -> subject по умолчанию
                subject = "SPDP notify";
                body = args[1];
            } else {
                // Три аргумента: email, subject, тело
                subject = args[1];
                body = args[2];
            }

            // Настройки SMTP (как в оригинале)
            String smtpHost = "gw.alfaintra.net";
            String smtpPort = "25";
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            Session session = Session.getInstance(props, null);

            sendEmail(session, toEmail, subject, body);
            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("EMail util ended!");
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body) throws Exception {
        MimeMessage msg = new MimeMessage(session);
        // Заголовки из оригинального байт-кода
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");
        msg.setFrom(new InternetAddress("noreply@alfa-bank.info"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(body, "text/html; charset=utf-8");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}