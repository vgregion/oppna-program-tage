package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Request;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import java.util.logging.Level;

/**
 * Controller handling viewing requests
 *
 */
@Controller
@RequestMapping({"VIEW", "EDIT"})
public class ViewRequestController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ModelAttribute("request")
	public Request loadRequest(@RequestParam(value="requestId", required=false) Integer requestId, PortletRequest portletRequest) {
		return null;
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