package com.example.yu.framework.servlet;

import com.example.yu.framework.helper.ConfigHelper;
import com.example.yu.framework.init.ProgramInit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        // 初始化相关 Helper 类
        ProgramInit.doInit();
        // 获取 ServletContext 对象（用于注册 Servlet）
        ServletContext servletContext = servletConfig.getServletContext();
        // 注册处理 JSP 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    /**
     * Receives standard HTTP requests from the public service method and dispatches them to the doXXX methods defined in this class.
     *
     * 1、从 HttpServletRequest 中获取请求方法和请求路径
     * 2、获取 YuRequestMapping Handler处理器 TODO:适配器方式实现找处理器
     * 3、从 HttpServletRequest 中获取请求参数：
     *             Post - request.getParameterNames()
     *             Get - String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
     *             封装成入参Param
     * 4、调用 处理器
     * 5、根据返回值类型（Data、View）进行处理
     *
     *
     *
     * @param req   the {@link HttpServletRequest} object that
     *                  contains the request the client made of
     *                  the servlet
     *
     * @param resp  the {@link HttpServletResponse} object that
     *                  contains the response the servlet returns
     *                  to the client
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
