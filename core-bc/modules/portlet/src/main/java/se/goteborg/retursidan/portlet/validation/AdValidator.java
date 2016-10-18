package se.goteborg.retursidan.portlet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import se.goteborg.retursidan.model.entity.Advertisement;

import java.util.logging.Level;

@Component
public class AdValidator implements Validator {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public boolean supports(Class<?> clz) {
		return Advertisement.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Advertisement ad = (Advertisement)obj;

		if(ad.getTopCategory() == null || ad.getTopCategory().getId() == -1) {
			errors.rejectValue("topCategory", "topCategory.missing");
		}
		
		if(ad.getCategory() == null || ad.getCategory().getId() == -1) {
			errors.rejectValue("category", "category.missing");
		}

		if(ad.getUnit() == null || ad.getUnit().getId() == -1) {
			errors.rejectValue("unit", "unit.missing");
		}

		if(ad.getArea() == null || ad.getArea().getId() == -1) {
			errors.rejectValue("area", "area.missing");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.missing");
		
		PersonValidator personValidator = new PersonValidator();
		ValidationUtils.invokeValidator(personValidator, ad.getContact(), errors);

		ValidationUtils.rejectIfEmpty(errors, "pickupAddress", "pickupAddress.missing");
		
		if (errors.hasErrors()) {
			logger.debug(errors.toString());
		}
	}

}
