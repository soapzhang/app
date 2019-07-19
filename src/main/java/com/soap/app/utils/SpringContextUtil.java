package com.soap.app.utils;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class SpringContextUtil {
    /** Spring Web 容器变量名. */
    private static final String SPRING_WEB_APPLICATION_CONTEXT = FrameworkServlet.class
            .getName() + ".CONTEXT.dispatcherServlet";

    /** 当前请求Request对象. */
    private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();

    /** ServletContext上下文. */
    private static ServletContext servletContext = null;

    /** Spring Web上下文. */
    private static WebApplicationContext applicationContext = null;

    /** . */
    private SpringContextUtil() {
    }

    /**
     * 设置请求Request.
     *
     * @param request
     *            Request对象
     */
    public static void setRequest(HttpServletRequest request) {
        localRequest.set(request);
    }

    /**
     * 根据类型获取Spring Bean.
     *
     * @param <T>
     *            目标类型
     * @param requiredType
     *            目标类型
     * @return 目标对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        if (applicationContext == null) {
            applicationContext = getWebApplicationContext();
        }
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据名称和类型获取Bean
     * @param name 名称
     * @param requiredType 类型
     * @return
     */
    public static <T> T getBean(String name,Class<T> requiredType) {
        if (applicationContext == null) {
            applicationContext = getWebApplicationContext();
        }
        return applicationContext.getBean(name, requiredType);
    }
    /**
     * 获取ServletContext.
     *
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * 设置servletContext上下文.
     *
     * @param servletContext
     *            .
     */
    public static void setServletContext(ServletContext servletContext) {
        SpringContextUtil.servletContext = servletContext;
    }

    /**
     * 获取Spring上下文.
     *
     * @return .
     */
    private static WebApplicationContext getWebApplicationContext() {
        WebApplicationContext wac = (WebApplicationContext) servletContext
                .getAttribute(SPRING_WEB_APPLICATION_CONTEXT);
        if (wac == null) {
            throw new RuntimeException("未发现Spring上下文.");
        }
        return wac;
    }

    /**
     * 获取Request参数.
     *
     * @param param
     *            参数名
     * @return 参数值
     */
    public static String getRequestParameter(String param) {
        if (localRequest.get() == null) {
            throw new RuntimeException("未发现Request对象.");
        }
        return localRequest.get().getParameter(param);
    }

    /**
     * 获取Request参数.
     *
     * @param param
     *            参数名
     * @return 参数值
     */
    public static String[] getRequestParameters(String param) {
        if (localRequest.get() == null) {
            throw new RuntimeException("未发现Request对象.");
        }
        return localRequest.get().getParameterValues(param);
    }

    /**
     * 获取Session中参数.
     *
     * @param key
     * @return
     */
    public static Object getSessionAttr(String key) {
        if (localRequest.get() == null) {
            throw new RuntimeException("未发现Request对象.");
        }
        return localRequest.get().getSession().getAttribute(key);
    }

    /**
     * 获取当前请求
     *
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        return localRequest.get();
    }
}
