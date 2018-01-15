package se.goteborg.retursidan.service;

import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Service
public class MailService {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	JavaMailSender mailSender;

    public MailService() {
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");
    }

    public static Session createMailSession() throws SystemException {
        return MailServiceUtil.getSession();
    }

    public void sendMail(String[] email, String sender, String subject, String mailText) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			logger.info("Sending mail: " + mailText);
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sender);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(mailText.replace("{NEWLINE}", System.getProperty("line.separator")));

			Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
			mailSender.send(message);
			logger.info("Mail sent to " + Arrays.toString(email));
		} catch (MessagingException e) {
			logger.warn("Could not send mail", e);
		}
	}
}
