package se.goteborg.retursidan.portlet.controller.util;

import javax.portlet.PortletRequest;
import java.util.Map;

/**
 * @author Patrik Bergström
 */
public class P3PUtil {

    public static String getUserId(PortletRequest request) {
        Map<String, String> userInfo = (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);
        String info;
        if (userInfo != null) {
            info = userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID.toString());
        } else {
            return "";
        }
        return info;
    }

}
