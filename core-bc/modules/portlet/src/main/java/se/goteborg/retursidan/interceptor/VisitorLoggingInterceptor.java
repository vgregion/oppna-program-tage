package se.goteborg.retursidan.interceptor;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;

import se.goteborg.retursidan.portlet.controller.util.P3PUtil;
import se.goteborg.retursidan.service.VisitorLoggingService;

/**
 * Interceptor called for every request that logs the visit
 *
 */
public class VisitorLoggingInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	VisitorLoggingService visitorLogging;
	
	/**
	 * Log the visit using the user id, or "anonymous" if no user is logged in.
	 */
	@Override
	public boolean preHandleRender(RenderRequest request,
			RenderResponse response, Object handler) throws Exception {
		String uid = P3PUtil.getUserId(request);
		visitorLogging.logVisit(uid);
		return super.preHandleRender(request, response, handler);
	}
}
