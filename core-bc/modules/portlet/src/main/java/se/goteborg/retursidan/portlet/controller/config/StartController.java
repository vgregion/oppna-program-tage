package se.goteborg.retursidan.portlet.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.portlet.controller.BaseController;

@Controller
@RequestMapping("EDIT")
public class StartController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @RenderMapping
    public String render() {
        return "config/start";
    }

    @RenderMapping(params = "page=adminRequests")
    public String adminRequests() {
        return "config/admin_requests";
    }


}
