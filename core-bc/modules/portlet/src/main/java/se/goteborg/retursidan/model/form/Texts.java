package se.goteborg.retursidan.model.form;

import javax.portlet.PortletPreferences;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;

/**
 * Model bean containing all texts configured for the application
 *
 */
@Component
public class Texts extends GeneralModelBean {
	private String confirmCreateAdText;
	private String confirmBookingText;
	private String confirmRepublishText;
	private String confirmRemoveRequestText;
	private String bookingConfirmationText;
	private String mailSenderAddress;
	private String mailSubject;
	private String mailBody;

	public Texts() {
	}

	public Texts(PortletPreferences prefs) {
		this.confirmCreateAdText = prefs.getValue("confirmCreateAdText", "");
		this.confirmBookingText = prefs.getValue("confirmBookingText", "");
		this.confirmRepublishText = prefs.getValue("confirmRepublishText", "");
		this.confirmRemoveRequestText = prefs.getValue("confirmRemoveRequestText", "");
		this.bookingConfirmationText = prefs.getValue("bookingConfirmationText", "");
		this.mailSenderAddress = prefs.getValue("mailSenderAddress", "tage@vgregion.se");
		this.mailSubject = prefs.getValue("mailSubject", "Annons bokad!");
		this.mailBody = prefs.getValue("mailBody", "");
	}

	public String getConfirmCreateAdText() {
		return confirmCreateAdText;
	}

	public void setConfirmCreateAdText(String confirmCreateAdText) {
		this.confirmCreateAdText = confirmCreateAdText;
	}

	public String getConfirmBookingText() {
		return confirmBookingText;
	}

	public void setConfirmBookingText(String confirmBookingText) {
		this.confirmBookingText = confirmBookingText;
	}

	public String getConfirmRepublishText() {
		return confirmRepublishText;
	}

	public void setConfirmRepublishText(String confirmRepublishText) {
		this.confirmRepublishText = confirmRepublishText;
	}

	public String getConfirmRemoveRequestText() {
		return confirmRemoveRequestText;
	}

	public void setConfirmRemoveRequestText(String confirmRemoveRequestText) {
		this.confirmRemoveRequestText = confirmRemoveRequestText;
	}

	public String getBookingConfirmationText() {
		return bookingConfirmationText;
	}

	public void setBookingConfirmationText(String bookingConfirmationText) {
		this.bookingConfirmationText = bookingConfirmationText;
	}

	public String getMailSenderAddress() {
		return mailSenderAddress;
	}

	public void setMailSenderAddress(String mailSenderAddress) {
		this.mailSenderAddress = mailSenderAddress;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
}
