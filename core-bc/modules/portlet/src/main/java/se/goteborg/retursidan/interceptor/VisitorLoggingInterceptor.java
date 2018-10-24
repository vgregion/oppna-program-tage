package se.goteborg.retursidan.interceptor;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import se.goteborg.retursidan.portlet.controller.util.P3PUtil;
import se.goteborg.retursidan.service.VisitorLoggingService;

/**
 * Interceptor called for every request that logs the visit
 *
 */
public class VisitorLoggingInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	VisitorLoggingService visitorLogging;
	
	/**
	 * Log the visit using the user id, or "anonymous" if no user is logged in.
	 */
	@Override
	public boolean preHandleRender(RenderRequest request,
			RenderResponse response, Object handler) throws Exception {
		String uid = P3PUtil.getUserId(request);
		try {
			visitorLogging.logVisit(uid);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return super.preHandleRender(request, response, handler);
	}
}
