package se.goteborg.retursidan.portlet.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import se.goteborg.retursidan.model.form.Booking;

import java.util.logging.Level;

@Component
public class BookingValidator implements Validator {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	PersonValidator personValidator;
	
	@Override
	public boolean supports(Class<?> clz) {
		return Booking.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Booking booking = (Booking)obj;

		if (!booking.isTransportationConfirmed()) {
			errors.rejectValue("transportationConfirmed", "transportationConfirmed.mandatory");
		}

		ValidationUtils.invokeValidator(personValidator, booking.getContact(), errors);
		
		if (errors.hasErrors()) {
			logger.debug(errors.toString());
		}
	}

}
