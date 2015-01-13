package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.portlet.ModelAndView;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.portlet.controller.util.P3PUtil;
import se.goteborg.retursidan.service.ModelService;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Base class for all controllers, implements common functionality
 *
 */
public abstract class BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
	protected ModelService modelService;
	
	@Autowired
	protected MessageSource messageSource;
	
	@ModelAttribute("userId")
	public String getUserId(PortletRequest request) {
        /*if (request.getUserPrincipal() != null) {
			return request.getUserPrincipal().getName();
		} else {
			return "";
		}*/
        return P3PUtil.getUserId(request);
    }
	
	@ModelAttribute("config")
	public Config getConfig(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new Config(prefs);
	}

	@ModelAttribute("texts")
	public Texts getTexts(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new Texts(prefs);
	}

	/**
	 * Handle any uncaught exceptions during the request by redirecting
	 * the user to an error view where the exception is presented in a
	 * graceful way. 
	 * @param t The exception
	 * @return A ModelView object redirecting the user to the error view
	 */
	@ExceptionHandler(Throwable.class)
	public ModelAndView handleError(RuntimeException t) {
		ModelAndView modelAndView = new ModelAndView();
		logger.error("Uncaught exception thrown!", t);
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		modelAndView.getModelMap().addAttribute("error", t);
		modelAndView.getModelMap().addAttribute("stackTrace", sw.toString());
		modelAndView.setView("error");
		return modelAndView;
	}
}
