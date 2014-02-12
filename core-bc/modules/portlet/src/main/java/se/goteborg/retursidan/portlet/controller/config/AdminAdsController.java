package se.goteborg.retursidan.portlet.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.portlet.controller.PhotoBaseController;

import javax.portlet.*;

@Controller
@RequestMapping("EDIT")
public class AdminAdsController extends PhotoBaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private PortletContext portletContext;

    @RenderMapping(params="page=adminAds")
	public String adminAds( @RequestParam(value="pageIdx", required=false) Integer pageIdx, RenderRequest request, RenderResponse response, Model model) {
		PagedList<Advertisement> pagedList = modelService.getAllAdvertisements(null, (pageIdx != null) ? pageIdx : 1, getConfig(request).getPageSizeInt());
		model.addAttribute("ads", pagedList);
		return "config/admin_ads";
	}
	
	@ActionMapping("selectAdsPage")
	public void selectAds(@RequestParam(value="pageIdx", required=false) String pageIdx, Model model, ActionRequest request, ActionResponse response) {
		response.setRenderParameter("page", "adminAds");
		response.setRenderParameter("pageIdx", pageIdx);
	}
	
	@ActionMapping("removeAd")
	public void removeAd(@RequestParam(value="advertisementId", required=true) Integer advertisementId, ActionResponse response) {
		Advertisement ad = modelService.getAdvertisement(advertisementId);
		modelService.removeAdvertisement(ad);
		response.setRenderParameter("page", "adminAds");
	}
	
	@ActionMapping("expireAd")
	public void expireAd(@RequestParam(value="advertisementId", required=true) Integer advertisementId, ActionResponse response) {
		Advertisement ad = modelService.getAdvertisement(advertisementId);
		ad.setStatus(Status.EXPIRED);
		modelService.updateAd(ad);
		response.setRenderParameter("page", "adminAds");
	}
	@ActionMapping("republishAd")
	public void republishAd(@RequestParam(value="advertisementId", required=true) Integer advertisementId, ActionResponse response) {
		Advertisement ad = modelService.getAdvertisement(advertisementId);
		ad.setStatus(Status.PUBLISHED);
		modelService.updateAd(ad);
		response.setRenderParameter("page", "adminAds");
	}

    public void setPortletContext(PortletContext portletContext) {
        this.portletContext = portletContext;
    }

    public PortletContext getPortletContext() {
        return portletContext;
    }
	
}
