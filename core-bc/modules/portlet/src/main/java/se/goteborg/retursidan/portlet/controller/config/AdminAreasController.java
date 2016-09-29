package se.goteborg.retursidan.portlet.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.AreaValidator;

import javax.portlet.ActionResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("EDIT")
public class AdminAreasController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ModelAttribute("newArea")
	public Area getNewArea() {
		return new Area();
	}

	@ModelAttribute("removeArea")
	public Area getRemoveArea() {
		return new Area();
	}

	@Autowired
	private AreaValidator areaValidator;
	
	@InitBinder("newArea")
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(areaValidator);
	}

	@RenderMapping(params="page=adminAreas")
	public String changeUnits(Model model) {
		List<Area> areas = modelService.getAreas();
		model.addAttribute("areas", areas);
		return "config/admin_areas";
	}
	
	@ActionMapping("saveArea")
	public void saveUnit(@Valid @ModelAttribute("newArea") Area area, BindingResult bindingResult, ActionResponse response, Model model) {
		if (!bindingResult.hasErrors()) {
			modelService.addArea(area);
			
			// clear the newUnit model attribute so that the input box is empty, and set the removeUnit
			// to the recently created unit so that it is selected in the list.
			model.addAttribute("newArea", new Area());
			model.addAttribute("removeArea", area);
		}
		response.setRenderParameter("page", "adminAreas");
	}
	
	@ActionMapping("removeArea")
	public void removeArea(@ModelAttribute("removeArea") Area area, ActionResponse response, Model model) {
		modelService.removeArea(area);
		response.setRenderParameter("page", "adminAreas");
	}

}
