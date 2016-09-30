package se.goteborg.retursidan.portlet.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.portlet.controller.BaseController;
import se.goteborg.retursidan.service.StatisticsService;
import se.goteborg.retursidan.service.VisitorLoggingService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("EDIT")
public class StatisticsController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private VisitorLoggingService visitorLogging;
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RenderMapping(params="page=statistics")
	public String showStatistics(RenderRequest request, RenderResponse response, Model model) {
		Integer uniqueVisitors = visitorLogging.getUniqueVisitors();
		model.addAttribute("uniqueVisitors", uniqueVisitors);
		
		List<Unit> units = modelService.getUnits();
		model.addAttribute("units", units);

		List<Area> areas = modelService.getAreas();
		model.addAttribute("areas", areas);

		Integer totalNumberOfAds = statisticsService.getTotalNumberOfAds();
		model.addAttribute("totalNumberOfAds", totalNumberOfAds);
		List<Integer> unitAdCount = new ArrayList<Integer>();
		for (Unit unit : units) {
			Integer count = statisticsService.getTotalAdsForUnit(unit);
			unitAdCount.add(count);
		}
		model.addAttribute("unitAdCount", unitAdCount);

		Integer totalNumberOfAreaAds = statisticsService.getTotalNumberOfAdsWithArea();
		model.addAttribute("totalNumberOfAreaAds", totalNumberOfAreaAds);
		List<Integer> areaAdCount = new ArrayList<Integer>();
		for (Area area : areas) {
			Integer count = statisticsService.getTotalAdsForArea(area);
			areaAdCount.add(count);
		}
		model.addAttribute("areaAdCount", areaAdCount);
		
		Integer totalNumberOfRequests = statisticsService.getTotalNumberOfRequests();
		model.addAttribute("totalNumberOfRequests", totalNumberOfRequests);
		List<Integer> unitRequestCount = new ArrayList<Integer>();
		for (Unit unit : units) {
			Integer count = statisticsService.getTotalRequestsForUnit(unit);
			unitRequestCount.add(count);
		}
		model.addAttribute("unitRequestCount", unitRequestCount);

		Integer totalNumberOfAreaRequests = statisticsService.getTotalNumberOfRequestsWithArea();
		model.addAttribute("totalNumberOfAreaRequests", totalNumberOfAreaRequests);
		List<Integer> areaRequestCount = new ArrayList<Integer>();
		for (Area area : areas) {
			Integer count = statisticsService.getTotalRequestsForArea(area);
			areaRequestCount.add(count);
		}
		model.addAttribute("areaRequestCount", areaRequestCount);

		Integer totalNumberOfBookedAds = statisticsService.getTotalNumberOfBookedAds();
		model.addAttribute("totalNumberOfBookedAds", totalNumberOfBookedAds);
		List<Integer> unitBookedCount = new ArrayList<Integer>();
		for (Unit unit : units) {
			Integer count = statisticsService.getBookedAdsForUnit(unit);
			unitBookedCount.add(count);
		}
		model.addAttribute("unitBookedCount", unitBookedCount);
		
		Integer totalNumberOfBookedAreaAds = statisticsService.getTotalNumberOfBookedAdsWithArea();
		model.addAttribute("totalNumberOfBookedAreaAds", totalNumberOfBookedAreaAds);
		List<Integer> areaBookedCount = new ArrayList<Integer>();
		for (Area area : areas) {
			Integer count = statisticsService.getBookedAdsForArea(area);
			areaBookedCount.add(count);
		}
		model.addAttribute("areaBookedCount", areaBookedCount);

		model.addAttribute("totalNumberOfExpiredAds", statisticsService.getTotalNumberOfExpiredAds());
		return "config/statistics";
	}
}
