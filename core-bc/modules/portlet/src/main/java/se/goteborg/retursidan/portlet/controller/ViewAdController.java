package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Advertisement;

import java.util.logging.Level;

/**
 * Controller handling viewing of ads
 *
 */
@Controller
@RequestMapping({"VIEW", "EDIT"})
public class ViewAdController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ModelAttribute("advertisement")
	public Advertisement loadAdvertisement(@RequestParam(value="advertisementId", required=false) Integer advertisementId) {
		if (advertisementId != null) {
			logger.trace("loading advertisement id=" + advertisementId);
			Advertisement advertisement = modelService.getAdvertisement(advertisementId);
			logger.trace("advertisement=" + advertisement);
			return advertisement;
		}
		return null;
	}
	
	@RenderMapping(params="page=viewAd")
	public String viewAd(@RequestParam(value="previousPage", required=false) String previousPage, Model model) {
		if (previousPage != null) {
			model.addAttribute("previousPage", previousPage);
		}
		return "view_ad";
	}
}
