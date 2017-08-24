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
import se.goteborg.retursidan.model.entity.Request;

import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;

/**
 * Controller handling viewing of ads
 *
 */
@Controller
@RequestMapping({"VIEW", "EDIT"})
public class ViewAdAndRequestController extends BaseController {
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

	@RenderMapping(params = {"page=viewAd", "externalPage=none"})
	public String viewAd(@RequestParam(value = "previousPage", required = false) String previousPage, RenderRequest request, Model model) {
		if (request.getPortletMode().equals(PortletMode.EDIT)) {
			Advertisement advertisement = (Advertisement) model.asMap().get("advertisement");

			if (advertisement != null && advertisement.getBooker() != null) {
				model.addAttribute("booker", advertisement.getBooker());
			}
		}
		if (previousPage != null) {
			model.addAttribute("previousPage", previousPage);
		}
		return "view_ad";
	}

	@RenderMapping(params="externalPage=viewRequest")
	public String viewRequestFromOther(RenderRequest portletRequest, Model model) {
		// read public render parameter
		Integer externalRequestId = null;
		if (portletRequest.getParameter("externalRequestId") != null) {
			externalRequestId = new Integer(portletRequest.getParameter("externalRequestId"));
		}
		return viewRequest(externalRequestId, null, model);
	}

	@RenderMapping(params="page=viewRequest")
	public String viewRequest(@RequestParam(value="requestId", required=false) Integer requestId, @RequestParam(value="previousPage", required=false) String previousPage, Model model) {
		if (requestId != null) {
			logger.trace("loading request id=" + requestId);
			Request request = modelService.getRequest(requestId);
			logger.trace("request=" + request);
			model.addAttribute("request", request);
		}
		if (previousPage != null) {
			model.addAttribute("previousPage", previousPage);
		}
		return "view_request";
	}
}
