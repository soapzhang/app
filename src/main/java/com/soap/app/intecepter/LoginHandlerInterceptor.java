package com.soap.app.intecepter;

import com.soap.app.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("msg", "请先登录！");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
