package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Advertisement;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import java.util.logging.Level;

/**
 * Controller handling republish of ads
 *
 */
@Controller
@RequestMapping("VIEW")
public class RepublishAdController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ModelAttribute("advertisement")
	public Advertisement getAdvertisement(@RequestParam(value="advertisementId", required=false) Integer advertisementId, PortletRequest request) {
		return modelService.getAdvertisement(advertisementId);
	}
	
	@RenderMapping(params="page=republishAd")
	public String republishAd() {
		return "republish_ad";
	}

	@ActionMapping("performRepublishing")
	public void performRepublishing(@ModelAttribute("advertisement") Advertisement advertisement, ActionRequest request, ActionResponse response) {
		logger.trace("Republishing ad with id=" + advertisement.getId());
		advertisement.setStatus(Advertisement.Status.PUBLISHED);
		modelService.updateAd(advertisement);
	}
}
