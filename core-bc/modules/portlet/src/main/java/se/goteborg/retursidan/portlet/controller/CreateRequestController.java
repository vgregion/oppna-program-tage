package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.validation.RequestValidator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller handling creating requests
 *
 */
@Controller
@RequestMapping("VIEW")
public class CreateRequestController extends CreateController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@InitBinder("request")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new RequestValidator());	
	}
	
	@RenderMapping(params="externalPage=createRequest")
	public String createAdExternal(PortletRequest portletRequest, Model model) {
		Request request = new Request();
		populateContactInfoFromLdap(portletRequest, request);

		model.addAttribute("request", request);
		
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);
	
		List<Area> areas = modelService.getAreas();
		model.addAttribute("areas", areas);

		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		return "create_request";
	}

	@RenderMapping(params="page=createRequest")
	public String createAd(PortletRequest portletRequest, Model model) {
		// work-around for Spring form bug/misbehavior, errors are not persisted in model 
		Request request;
		if(model.containsAttribute("request")) {
			request = (Request)model.asMap().get("request");

			if (request.getContact() == null) {
				populateContactInfoFromLdap(portletRequest, request);
			}
		} else {
			request = new Request();
			populateContactInfoFromLdap(portletRequest, request);
			model.addAttribute("request", request);
		}
		
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);

		List<Area> areas = modelService.getAreas();
		model.addAttribute("areas", areas);

		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		if (request.getTopCategory() != null && request.getTopCategory().getId() > 0) {
			List<Category> subCategories = modelService.getSubCategories(request.getTopCategory().getId());
			model.addAttribute("subCategories", subCategories);		
		}	
		return "create_request";
	}
	
	@ActionMapping("saveRequest")
	public void saveAd(@Valid @ModelAttribute("request") Request requestAd, BindingResult bindingResult, ActionRequest request, ActionResponse response, Model model) {
		requestAd.setCreatorUid(getUserId(request));
		response.setRenderParameter("externalPage", "none");
		if (!bindingResult.hasErrors()) {
			logger.trace("Saving request: " + requestAd);
			requestAd.setStatus(Request.Status.PUBLISHED);
			int id = modelService.saveRequest(requestAd);
			logger.trace("Request saved with id = " + id);
			
			// reload the request into the model to get all data populated
			model.addAttribute("request", modelService.getRequest(id));

			response.setRenderParameter("page", "viewRequest");		
		} else {
			response.setRenderParameter("page", "createRequest");			
		}
	}

}
