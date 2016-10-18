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
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.validation.UnitValidator;

import javax.portlet.ActionResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("EDIT")
public class AdminUnitsController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ModelAttribute("newUnit")
	public Unit getNewUnit() {
		return new Unit();
	}

	@ModelAttribute("removeUnit")
	public Unit getRemoveUnit() {
		return new Unit();
	}

	@Autowired
	private UnitValidator unitValidator;
	
	@InitBinder("newUnit")  
	protected void initBinder(WebDataBinder binder) {  
		binder.setValidator(unitValidator);
	}

	@RenderMapping(params="page=adminUnits")
	public String changeUnits(Model model) {
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);
		return "config/admin_units";
	}
	
	@ActionMapping("saveUnit")
	public void saveUnit(@Valid @ModelAttribute("newUnit") Unit unit, BindingResult bindingResult, ActionResponse response, Model model) {
		if (!bindingResult.hasErrors()) {
			modelService.addUnit(unit);
			
			// clear the newUnit model attribute so that the input box is empty, and set the removeUnit
			// to the recently created unit so that it is selected in the list.
			model.addAttribute("newUnit", new Unit());
			model.addAttribute("removeUnit", unit);
		}
		response.setRenderParameter("page", "adminUnits");
	}
	
	@ActionMapping("removeUnit")
	public void removeUnit(@ModelAttribute("removeUnit") Unit unit, ActionResponse response, Model model) {
		modelService.removeUnit(unit);
		response.setRenderParameter("page", "adminUnits");
	}

}
