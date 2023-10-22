package com.example.yu.framework.servlet;

import com.example.yu.framework.helper.ConfigHelper;
import com.example.yu.framework.init.ProgramInit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * request forwarder
 * <p>
 * urlPatterns = "/*": This indicates that the servlet should handle all requests to the application.
 * <p>
 * loadOnStartup = 0: This attribute specifies the order in which the servlet should be loaded.
 * A value of 0 indicates that the servlet should be loaded on the application's startup.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ProgramInit.doInit();
        //获取 ServletContext 对象（用于注册 Servlet）
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
    }
}
