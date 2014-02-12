package se.goteborg.retursidan.portlet.validation;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import se.goteborg.retursidan.model.form.Config;

import java.util.logging.Level;

@Component
public class ConfigValidator implements Validator {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public boolean supports(Class<?> clz) {
		return Config.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Config config = (Config)obj;

		ValidationUtils.rejectIfEmpty(errors, "pageSize", "config.pagesize.empty");
		if (!StringUtils.isNumeric(config.getPageSize())) {
			errors.rejectValue("pageSize", "config.pagesize.notnumber");
		}
		ValidationUtils.rejectIfEmpty(errors, "imageWidth", "config.width.empty");
		if (!StringUtils.isNumeric(config.getImageWidth())) {
			errors.rejectValue("imageWidth", "config.width.notnumber");
		}
		ValidationUtils.rejectIfEmpty(errors, "imageHeight", "config.height.empty");
		if (!StringUtils.isNumeric(config.getImageHeight())) {
			errors.rejectValue("imageheight", "config.height.notnumber");
		}
		ValidationUtils.rejectIfEmpty(errors, "thumbWidth", "config.width.empty");
		if (!StringUtils.isNumeric(config.getThumbWidth())) {
			errors.rejectValue("thumbWidth", "config.width.notnumber");
		}
		ValidationUtils.rejectIfEmpty(errors, "thumbHeight", "config.height.empty");
		if (!StringUtils.isNumeric(config.getThumbHeight())) {
			errors.rejectValue("thumbHeight", "config.height.notnumber");
		}
		ValidationUtils.rejectIfEmpty(errors, "adExpireTime", "config.expiretime.empty");
		if (!StringUtils.isNumeric(config.getAdExpireTime())) {
			errors.rejectValue("adExpireTime", "config.expiretime.notnumber");
		}
		ValidationUtils.rejectIfEmpty(errors, "requestExpireTime", "config.expiretime.empty");
		if (!StringUtils.isNumeric(config.getRequestExpireTime())) {
			errors.rejectValue("requestExpireTime", "config.expiretime.notnumber");
		}		
		
		ValidationUtils.rejectIfEmpty(errors, "pocURIBase", "config.pocuribase.empty");

		ValidationUtils.rejectIfEmpty(errors, "rulesUrl", "config.rulesurl.empty");
		
		if (errors.hasErrors()) {
			logger.debug(errors.toString());
		}
	}

}
