package se.goteborg.retursidan.portlet.defaultcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.model.form.SearchFilter;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.service.ModelService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;
import java.util.logging.Level;

/**
 * Default controller, handling all requests that are not handled by another controller.
 * Placed in its own package just to avoid a strange bug/feature of Spring that puts the 
 * controllers in a list ordered by their name and if the controller with a "catch all"
 * render mapping is placed before other controllers, it will consume requests that really
 * should have been handled by the controller further down in the list.
 */
@Controller
@RequestMapping("VIEW")
@SessionAttributes(types = SearchFilter.class)
public class ListAdsController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private ModelService modelService;
	
	@ModelAttribute
	public SearchFilter createSearchFilter() {
		return new SearchFilter();
	}
	
	@RenderMapping
	public String listAds(@ModelAttribute SearchFilter searchFilter, @RequestParam(value="pageIdx", required=false) Integer pageIdx, RenderRequest request, RenderResponse response, Model model) {
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);
	
		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		if (searchFilter.getTopCategory() != null && searchFilter.getTopCategory().getId() > 0) {
			List<Category> subCategories = modelService.getSubCategories(searchFilter.getTopCategory().getId());
			model.addAttribute("subCategories", subCategories);		
		}
		
		if (!model.containsAttribute("ads")) {
			PagedList<Advertisement> pagedList = modelService.getAllAdvertisements(Advertisement.Status.PUBLISHED, (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
			model.addAttribute("ads", pagedList);
		}
		return "list_ads";
	}


	@RenderMapping(params="page=listMyAds")
	public String listMyAds(@ModelAttribute("userId") String userId, @ModelAttribute SearchFilter searchFilter, @RequestParam(value="pageIdx", required=false) Integer pageIdx, RenderRequest request, RenderResponse response, Model model) {
		List<Category> topCategories = modelService.getTopCategories();
		model.addAttribute("topCategories", topCategories);

		if (searchFilter.getTopCategory() != null && searchFilter.getTopCategory().getId() > 0) {
			List<Category> subCategories = modelService.getSubCategories(searchFilter.getTopCategory().getId());
			model.addAttribute("subCategories", subCategories);		
		}
		
		if (!model.containsAttribute("ads")) {
			PagedList<Advertisement> pagedList = modelService.getAllAdvertisementsForUid(userId, (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
			model.addAttribute("ads", pagedList);
		}
		return "list_my_ads";		
	}
	
	@ActionMapping("filterAds")
	public void filterAds(@ModelAttribute SearchFilter searchFilter, @RequestParam(value="pageIdx", required=false) Integer pageIdx, Model model, ActionRequest request, ActionResponse response) {
		PagedList<Advertisement> pagedList = modelService.getAllFilteredAdvertisements(Advertisement.Status.PUBLISHED, searchFilter.getTopCategory(), searchFilter.getSubCategory(), searchFilter.getUnit(), (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
		logger.trace("Ad list filtered using " + searchFilter + " for page " + pageIdx + ", returing " + ((pagedList.getList() != null) ? pagedList.getList().size() : "null") + " ads.");
		model.addAttribute("ads", pagedList);
		response.setRenderParameter("page", "listAds");
	}

	@ActionMapping("filterMyAds")
	public void filterMyAds(@ModelAttribute("userId") String userId, @ModelAttribute SearchFilter searchFilter, @RequestParam(value="pageIdx", required=false) Integer pageIdx, Model model, ActionRequest request, ActionResponse response) {
		PagedList<Advertisement> pagedList = modelService.getAllFilteredAdvertisementsForUid(userId, searchFilter.getTopCategory(), searchFilter.getSubCategory(), searchFilter.getUnit(), (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
		logger.trace("Ad list for uid=" + userId + " filtered using " + searchFilter + " for page " + pageIdx + ", returing " + ((pagedList.getList() != null) ? pagedList.getList().size() : "null") + " ads.");
		model.addAttribute("ads", pagedList);
		response.setRenderParameter("page", "listMyAds");
	}
}
