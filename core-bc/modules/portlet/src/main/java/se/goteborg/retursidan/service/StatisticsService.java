package se.goteborg.retursidan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.RequestDAO;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Unit;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class StatisticsService {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private AdvertisementDAO advertisementDAO;

	@Autowired
	private RequestDAO requestDAO;

	public Integer getTotalNumberOfAds() {
		return advertisementDAO.count();
	}

	public Integer getTotalNumberOfRequests() {
		return requestDAO.count();
	}

	public Integer getTotalAdsForUnit(Unit unit) {
		return advertisementDAO.count(unit);
	}

	public Integer getTotalRequestsForUnit(Unit unit) {
		return requestDAO.count(unit);
	}

	public Integer getTotalNumberOfBookedAds() {
		return advertisementDAO.count(Status.BOOKED);
	}

	public Integer getBookedAdsForUnit(Unit unit) {
		return advertisementDAO.count(Status.BOOKED, unit);
	}

	public Integer getTotalNumberOfExpiredAds() {
		return advertisementDAO.count(Status.EXPIRED);
	}

	public Integer getTotalNumberOfAdsWithArea() {
		return advertisementDAO.countNonNullArea(null);
	}

	public Integer getTotalNumberOfRequestsWithArea() {
		return requestDAO.countNonNullArea();
	}

	public Integer getTotalAdsForArea(Area area) {
		return advertisementDAO.count(null, area);
	}

	public Integer getTotalRequestsForArea(Area area) {
		return requestDAO.count(area);
	}

	public Integer getTotalNumberOfBookedAdsWithArea() {
		return advertisementDAO.countNonNullArea(Status.BOOKED);
	}

	public Integer getBookedAdsForArea(Area area) {
		return advertisementDAO.count(Status.BOOKED, area);
	}
}