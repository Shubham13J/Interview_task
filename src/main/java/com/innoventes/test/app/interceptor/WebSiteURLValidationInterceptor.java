package com.innoventes.test.app.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Task 5: Create an interceptor:
//        Create an interceptor only for the POST methods
//        - The interceptor should inspect the request payload and set the webSiteURL field to null if the
//        URL is not valid

public class WebSiteURLValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("POST".equalsIgnoreCase(request.getMethod())) {

            if (request.getParameter("webSiteURL") != null && request.getParameter("webSiteURL").contains("invalid")) {
                request.setAttribute("webSiteURL", null);
            }
        }

        return true;
    }
}
