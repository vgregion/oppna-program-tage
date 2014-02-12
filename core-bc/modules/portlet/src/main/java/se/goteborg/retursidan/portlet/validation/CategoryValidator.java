package se.goteborg.retursidan.portlet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import se.goteborg.retursidan.model.entity.Category;

import java.util.logging.Level;

@Component
public class CategoryValidator implements Validator {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public boolean supports(Class<?> clz) {
		return Category.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "category.title.missing");
	
		if (errors.hasErrors()) {
			logger.debug(errors.toString());
		}
	}

}
