package se.goteborg.retursidan.portlet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletContextAware;
import se.goteborg.retursidan.model.PhotoHolder;

import javax.portlet.PortletContext;
import javax.portlet.ResourceResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.logging.Level;

/**
 * Controller responsible for serving up photos loaded from the database.
 * 
 */
@Controller
@RequestMapping("VIEW")
public class PhotoController extends PhotoBaseController implements PortletContextAware {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private PortletContext portletContext = null;
	
	@Override
	public void setPortletContext(PortletContext portletContext) {
		this.portletContext = portletContext;
	}

    @Override
    public PortletContext getPortletContext() {
        return portletContext;
    }
}
