package com.jtd.ticketing.base.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sunll.
 */
public class SessionUtil {
    /**
     * 用来获取session中的当前登录用户
     */
    public static final String GET_USER_FORM_SESSION_ATTRIBUTE_NAME = "adminLoginUser";

    /**
     * 根据AttributeName从session中获取值
     *
     * @param request HttpServletRequest
     * @return 执行结果
     */
    public static Object getValueByAttributeName(HttpServletRequest request) {
        return request.getSession().getAttribute(GET_USER_FORM_SESSION_ATTRIBUTE_NAME);
    }
}
