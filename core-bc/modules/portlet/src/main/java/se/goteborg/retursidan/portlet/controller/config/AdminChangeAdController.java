package se.goteborg.retursidan.portlet.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import se.goteborg.retursidan.portlet.controller.ChangeAdController;

/**
 * Controller handling changing ads
 *
 */
@Controller
@RequestMapping("EDIT")
public class AdminChangeAdController extends ChangeAdController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

}
