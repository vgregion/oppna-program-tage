package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Request;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

/**
 * Controller handling manual expire of requests
 *
 */
@Controller
@RequestMapping("VIEW")
public class ExpireRequestController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ModelAttribute("request")
	public Request getRequest(@RequestParam(value="requestId", required=false) Integer requestId, PortletRequest request) {
		return modelService.getRequest(requestId);
	}
	
	@RenderMapping(params="page=expireRequest")
	public String expireRequest() {
		return "expire_request";
	}

	@ActionMapping("performExpire")
	public void performExpire(@ModelAttribute("request") Request request, ActionRequest portletRequest, ActionResponse portletResponse) {
		logger.trace("Expiring request with id=" + request.getId());
		request.setStatus(Request.Status.EXPIRED);
		modelService.updateRequest(request);
		portletResponse.setRenderParameter("externalPage", "none");
	}
}
