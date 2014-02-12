package se.goteborg.retursidan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.RequestDAO;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ExpireService {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	AdvertisementDAO advertisementDAO;
	
	@Autowired
	RequestDAO requestDAO;
	
	public void expireAds(int days) {
		logger.debug("Expiring ads older than " + days + " days.");
		int count = advertisementDAO.expireOldAds(days);
		logger.debug(count + " ads were expired.");
	}

	public void expireRequests(int days) {
		logger.debug("Expiring requests older than " + days + " days.");
		int count = requestDAO.expireOldRequests(days);
		logger.debug(count + " requests were expired.");
	}
}
