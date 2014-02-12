package se.goteborg.retursidan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.exceptions.AdvertisementAlreadyBookedException;
import se.goteborg.retursidan.exceptions.AdvertisementExpiredException;
import se.goteborg.retursidan.exceptions.AdvertisementNotFoundException;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.Texts;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookingService {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private AdvertisementDAO advertisementDAO;

	@Autowired
	private MailService mailService;
	
	public void bookAdvertisement(Integer advertisementId, Person contact, Texts texts, Config config, String adLink) {
		logger.debug("Trying to book advertisement id=" + advertisementId + " for " + contact);
	
		Advertisement advertisement = advertisementDAO.findById(advertisementId);
		
		if (advertisement == null) {
			logger.warn("Could not find advertisement with id=" + advertisementId + " to book.");
			throw new AdvertisementNotFoundException(advertisementId);
		}
					
		// make sure only one thread can book at a time
		synchronized(this) {
			if (Advertisement.Status.BOOKED.equals(advertisement.getStatus())) {
				logger.warn("Advertisement with id=" + advertisementId + " is already booked.");
				throw new AdvertisementAlreadyBookedException(advertisementId);
			} else if (Advertisement.Status.EXPIRED.equals(advertisement.getStatus())) {
				logger.warn("Advertisement with id=" + advertisementId + " is expired and can not be booked.");
				throw new AdvertisementExpiredException(advertisementId);
			}
			advertisement.setBooker(contact);
			advertisement.setStatus(Advertisement.Status.BOOKED);
			advertisementDAO.merge(advertisement);
			
			String bookerMail = contact.getEmail();
			String advertiserMail = advertisement.getContact().getEmail();
			
			// send confirmation mails
			String mailBody = texts.getMailBody();
			mailBody = mailBody.replaceAll("\\{title\\}", advertisement.getTitle());
			mailBody = mailBody.replaceAll("\\{bookerName\\}", contact.getName());
			mailBody = mailBody.replaceAll("\\{bookerPhone\\}", contact.getPhone());
			mailBody = mailBody.replaceAll("\\{bookerMail\\}", contact.getEmail());
			mailBody = mailBody.replaceAll("\\{advertiserName\\}", advertisement.getContact().getName());
			mailBody = mailBody.replaceAll("\\{advertiserPhone\\}", advertisement.getContact().getPhone());
			mailBody = mailBody.replaceAll("\\{advertiserMail\\}", advertisement.getContact().getEmail());
			mailBody = mailBody.replaceAll("\\{link\\}", adLink);
			mailBody = mailBody + "\n\nLänk till regelverk:" + config.getRulesUrl();
			mailService.sendMail(new String[] {bookerMail, advertiserMail}, texts.getMailSenderAddress(), texts.getMailSubject(), mailBody);
		}
	}
}
