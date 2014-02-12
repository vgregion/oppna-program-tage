package se.goteborg.retursidan.portlet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import se.goteborg.retursidan.model.entity.Unit;

import java.util.logging.Level;

@Component
public class UnitValidator implements Validator {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public boolean supports(Class<?> clz) {
		return Unit.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "unit.name.missing");
	
		if (errors.hasErrors()) {
			logger.debug(errors.toString());
		}
	}

}
