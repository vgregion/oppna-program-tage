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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.binding.PhotoListPropertyEditor;
import se.goteborg.retursidan.portlet.validation.AdValidator;
import se.vgregion.ldapservice.LdapUser;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller handling creating ads
 *
 */
@Controller
@RequestMapping("VIEW")
public class CreateAdController extends CreateController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @InitBinder("advertisement")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new AdValidator());
		binder.registerCustomEditor(List.class, "photos", new PhotoListPropertyEditor());	
	}

	@ModelAttribute("advertisement")
	public Advertisement createAdvertisement() {
		return new Advertisement();
	}
		
	@RenderMapping(params="page=createAdConfirm")
	public String render(@RequestParam(value = "copyAdvertisementId", required = false) Integer copyAdvertisementId,
						 Model model) {
		model.addAttribute("copyAdvertisementId", copyAdvertisementId);
		return "create_ad_confirm";
	}

	
	@RenderMapping(params="page=createAd")
	public String createAd(PortletRequest request,
						   @RequestParam(value = "copyAdvertisementId", required = false) Integer copyAdvertisementId,
						   Model model) {
		Advertisement advertisement;

		if (copyAdvertisementId != null) {
			// We want to base our new ad on this ad.
			Advertisement baseAd = modelService.getAdvertisement(copyAdvertisementId);

			advertisement = new Advertisement();
			advertisement.setArea(baseAd.getArea());
			advertisement.setPickupAddress(baseAd.getPickupAddress());
			advertisement.setUnit(baseAd.getUnit());
			advertisement.setPickupConditions(baseAd.getPickupConditions());

			advertisement.setContact(baseAd.getContact());

			model.asMap().put("advertisement", advertisement);
		} else if(model.containsAttribute("advertisement")) {
			advertisement = (Advertisement)model.asMap().get("advertisement");

			if (advertisement.getContact() == null) {
				populateContactInfoFromLdap(request, advertisement);
			}
		} else {
			advertisement = new Advertisement();

			populateContactInfoFromLdap(request, advertisement);
		}

		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);
	
		List<Area> areas = modelService.getAreas();
		model.addAttribute("areas", areas);

		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		if (advertisement.getTopCategory() != null && advertisement.getTopCategory().getId() > 0) {
			List<Category> subCategories = modelService.getSubCategories(advertisement.getTopCategory().getId());
			model.addAttribute("subCategories", subCategories);		
		}

		return "create_ad";
	}

	@ActionMapping("saveAd")
	public void saveAd(@Valid @ModelAttribute("advertisement") Advertisement advertisement, BindingResult bindingResult,
                       ActionRequest request, ActionResponse response, Model model) {
		String userId = getUserId(request);
		advertisement.setCreatorUid(userId);
		if (!bindingResult.hasErrors()) {
			logger.trace("Saving advertisement: " + advertisement);

			if (request.getParameter("saveDraft") != null) {
				advertisement.setStatus(Advertisement.Status.DRAFT);
			} else {
				advertisement.setStatus(Advertisement.Status.PUBLISHED);
			}

			LdapUser ldapUser = userDirectoryService.getLdapUserByUid(userId);

			if (ldapUser != null) {
				advertisement.setDepartment(ldapUser.getAttributeValue("department"));
				advertisement.setDivision(ldapUser.getAttributeValue("division"));
			}

			int id = modelService.saveAd(advertisement);
			logger.trace("Advertisement saved with id = " + id);
			
			// reload the ad into the model to get all data populated
			model.addAttribute("advertisement", modelService.getAdvertisement(id));
			response.setRenderParameter("page", "viewAd");
		} else {
			response.setRenderParameter("page", "createAd");			
		}
	}	
}
