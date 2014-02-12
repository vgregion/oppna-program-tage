package se.goteborg.retursidan.portlet.efterlysningar.controller;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.goteborg.retursidan.model.entity.Request.Status;
import se.goteborg.retursidan.service.ModelService;

/**
 * Controller for the Efterlysningar portlet
 *
 */
@Controller()
@RequestMapping("VIEW")
public class RequestPortletController {
	
	@Autowired
	private ModelService modelService;
	
	@RenderMapping
	public String render(RenderRequest request, RenderResponse response, Model model) {
		model.addAttribute("requests", modelService.getAllRequests(Status.PUBLISHED));
		return "list_requests";
	}
}
