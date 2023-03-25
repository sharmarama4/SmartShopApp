package com.smart.smartcontactmanager.service;
import org.springframework.stereotype.Service;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

@Service
public class EmailService {

    public boolean sendEmail(String subject,String message,String to) {
        //rest of the code
        boolean f=false;
        String from="sharmarama418@gmail.com";
        //variable for gmail
        String host="smtp.gmail.com";
        //get the system properties
        Properties properties=System.getProperties();
        System.out.println("PROPERTIES:"+properties);
        //setting important information to properties object
        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable",true);
        properties.put("mail.smtp.auth",true);

        //step 1:to get the session object
        Session session=Session.getInstance(properties,new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("sharmarama418@gmail.com","mqefhtxnqjtlsrjo");
            }
        });
     session.setDebug(true);


//step2:compose the message

        MimeMessage m=new MimeMessage(session);
        try {
            //from email
          m.setFrom(from);

            //adding recipient to message

            m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            //adding subject to message
            m.setSubject(subject);
            //adding text to message
            m.setText(message);
            //send:send the message using transport class
            Transport.send(m);
            System.out.println("Sent success.....");
            f=true;
        }catch (MessagingException e){
            e.printStackTrace();

}
     return f;
             }

    }

