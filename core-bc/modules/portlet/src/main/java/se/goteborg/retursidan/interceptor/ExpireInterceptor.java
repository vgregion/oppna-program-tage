package se.goteborg.retursidan.interceptor;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.form.Config;
import se.goteborg.retursidan.model.form.Texts;
import se.goteborg.retursidan.service.ExpireService;
import se.goteborg.retursidan.service.MailService;
import se.goteborg.retursidan.util.CreateURLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Request interceptor that is configured in the portlet Spring XML. Is run at a
 * preset interval when a render request is issued and expires any ads and requests that
 * are older than the configured expire time.
 */
public class ExpireInterceptor extends HandlerInterceptorAdapter {
	private static long lastRun = 0;
	private static final long INTERVAL = 60 * 1000; // 60 minutes

	@Autowired
	ExpireService expireService;

	@Autowired
	MailService mailService;

	/**
	 * This method is called before every render request, every 60 minutes (or more)
	 * it will call the expire methods on the ExpireService to expire ads and requests.
	 */
	@Override
	public boolean preHandleRender(RenderRequest request, RenderResponse response, Object handler) throws Exception {
        List<Advertisement> expiredAds = null;

        synchronized (this) {

			// only run once every interval
			if (lastRun < (System.currentTimeMillis() - INTERVAL) ) {
				lastRun = System.currentTimeMillis();
				Config config = new Config(request.getPreferences());
                expiredAds = expireService.expireAds(config.getAdExpireTimeInt());
				expireService.expireRequests(config.getRequestExpireTimeInt());
			}
		}

		// Outside of synchronized block
        if (expiredAds != null && expiredAds.size() > 0) {
            sendEmailsForExpiredAds(request, expiredAds);
        }

        return super.preHandleRender(request, response, handler);
	}

    private void sendEmailsForExpiredAds(RenderRequest request, List<Advertisement> advertisements) {

	    Config config = new Config(request.getPreferences());

        Map<String, List<Advertisement>> collect = groupAdsByEmail(advertisements);

        for (Map.Entry<String, List<Advertisement>> emailToAds : collect.entrySet()) {
            List<String> adRows = new ArrayList<>();

            for (Advertisement advertisement : emailToAds.getValue()) {
                String adRow = advertisement.getTitle()
                        + " - " + CreateURLHelper.createHttpBaseURL(request) + config.getPocURIBase()
                        + "ad/" + advertisement.getId();

                adRows.add(adRow);
            }

            String mailBody = getTexts(request).getExpireMailBody();
            mailBody = mailBody.replaceAll("\\{advertisements\\}", StringUtils.join(adRows, "{NEWLINE}"));

            mailService.sendMail(
                    new String[]{emailToAds.getKey()},
                    getTexts(request).getMailSenderAddress(),
                    "Utgångna annonser",
                    mailBody);
        }
    }

    static Map<String, List<Advertisement>> groupAdsByEmail(List<Advertisement> advertisements) {
        return advertisements.stream()
                .collect(
                        Collectors.groupingBy(ad -> ad.getContact().getEmail(), Collectors.toList())
                );
    }

    public Texts getTexts(PortletRequest request) {
		PortletPreferences prefs = request.getPreferences();
		return new Texts(prefs);
	}

}
